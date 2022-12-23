package com.idev4.admin.service;

import com.idev4.admin.domain.MwDsbltyRpt;
import com.idev4.admin.domain.MwDthRpt;
import com.idev4.admin.domain.MwExp;
import com.idev4.admin.domain.MwRcvryTrx;
import com.idev4.admin.dto.CollectUpFrontCashDto;
import com.idev4.admin.dto.MwDsbltyRptDTO;
import com.idev4.admin.dto.ReportDeathDTO;
import com.idev4.admin.feignclient.ServiceClient;
import com.idev4.admin.feignclient.SetupFeignClient;
import com.idev4.admin.repository.*;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

@Service
public class LoanServiceingService {

    //// update by Yousaf
    private final Logger log = LoggerFactory.getLogger(MwClntService.class);
    private final long FUNREAL_CHARGES = 5000;
    @Autowired
    MwDthRptRepository mwDthRptRepository;
    //Added by Areeba
    @Autowired
    MwDsbltyRptRepository mwDsbltyRptRepository;
    //Ended by Areeba
    @Autowired
    MwPrdRepository mwPrdRepository;
    @Autowired
    EntityManager em;
    @Autowired
    ServiceClient serviceClient;
    @Autowired
    SetupFeignClient setupFeignClient;
    @Autowired
    Utils utils;
    @Autowired
    MwExpRepository mwExpRepository;
    @Autowired
    MwRcvryTrxRepository mwRcvryTrxRepository;
    @Autowired
    Environment env;

    @Transactional
    public MwDthRpt addMwDthRpt(ReportDeathDTO dr, String user, String token) {
        Instant now = Instant.now();
        int loopError = 0; // added by yousaf to control error Dated 05-Sep-2022
        long seq = SequenceFinder.findNextVal(Sequences.DTH_RPT_SEQ);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(utils.parseStringDateToDate(dr.deathDt));

        String[] loanApps = dr.getPrntLoanAppSeq().split(",");

        for (String loanApp : loanApps) {
            try {
                log.error("ParentLoanAppSeq " + loanApp);
                log.debug("ParentLoanAppSeq " + loanApp);
                ResponseEntity r = serviceClient.reverseAdvanceRecoveries(dr.clntSeq, Long.parseLong(loanApp), date, token);
            } catch (Exception e) {
                log.debug(e.getStackTrace().toString());
                loopError = -1;
                log.error(e.getStackTrace().toString());
                break;
            }
            loopError = 0;
        }

        MwDthRpt entity = new MwDthRpt();

        if (loopError == 0) { // added by yousaf to control error Dated 05-Sep-2022

            Query query = em.createNativeQuery("SELECT FN_CALC_FUNERAL_CHARGES ('REPORT DEATH', " + dr.clntSeq
                    + ", '" + utils.parseDatetoLocalDate(dr.deathDt) + "' , '" + user + "') FROM DUAL");

            List listChargeAmt = query.getResultList();
            long amt = Long.parseLong(listChargeAmt.get(0).toString());
            // End by Zohaib Asim

            entity.setDthRptSeq(seq);
            entity.setClntSeq(dr.clntSeq);
            entity.setEffStartDt(now);
            entity.setClntNomFlg(dr.gender);
            entity.setDtOfDth(utils.parseStringDateToInstant(dr.deathDt));
            entity.setCauseOfDth(dr.dethCase);
            entity.setDthCertNum(dr.deathcertf);
            entity.setCrtdBy(user);
            entity.setCrtdDt(now);
            entity.setLastUpdBy(user);
            entity.setLastUpdDt(now);
            entity.setDelFlg(false);
            entity.setCrntRecFlg(true);
            entity.setAmt(amt);
            entity.setInsrClmSts(-1l);
            entity.setAdjFlg(-1l);
            mwDthRptRepository.save(entity);
            return entity;
        } else {
            return null;
        }
    }

    // Modified by Zohaib Asim - Dated 18-02-2021
    // reverseExcessRecoveries() will be called after Death Status updated
    public Long reverseMwDthRpt(Long dthRptSeq, String cmnt, String user, String token) {
        MwDthRpt entity = mwDthRptRepository.findOneByDthRptSeqAndCrntRecFlg(dthRptSeq, true);

        if (entity != null) {
            entity.setLastUpdBy(user);
            entity.setLastUpdDt(Instant.now());
            entity.setDelFlg(true);
            entity.setCrntRecFlg(false);
            mwDthRptRepository.save(entity);

            serviceClient.reverseExcessRecoveries(entity.getClntSeq(), token);
        }
        return dthRptSeq;
    }

    private long payFunral(long clntSeq) {
        // Modified by Zohaib Asim - Dated 29-12-2020 - Kashf Care Case for Funeral Payment
        Query query = em.createNativeQuery(
                        "select case when NVL(mhip.hlth_insr_plan_seq, 0) = 1263 then 0 else sum(psc.amt)-nvl(sum(rd.pymt_amt),0) end funeral_charges "
                                + " from mw_loan_app la\r\n"
                                + " join mw_pymt_sched_hdr psh on la.loan_app_seq=psh.loan_app_seq and psh.crnt_rec_flg=1\r\n"
                                + " join mw_pymt_sched_dtl psd on psh.pymt_sched_hdr_seq=psd.pymt_sched_hdr_seq and psd.crnt_rec_flg=1\r\n"
                                + " join mw_pymt_sched_chrg psc on psd.pymt_sched_dtl_seq=psc.pymt_sched_dtl_seq and psc.crnt_rec_flg=1\r\n"
                                + " left outer join mw_rcvry_dtl rd on rd.pymt_sched_dtl_seq=psd.pymt_sched_dtl_seq and rd.CHRG_TYP_KEY=psc.CHRG_TYPS_SEQ and rd.crnt_rec_flg=1\r\n"
                                + " left join mw_clnt_hlth_insr mchi on mchi.LOAN_APP_SEQ = la.LOAN_APP_SEQ AND mchi.CRNT_REC_FLG = 1\n" +
                                "   left join mw_hlth_insr_plan mhip on mhip.HLTH_INSR_PLAN_SEQ = mchi.HLTH_INSR_PLAN_SEQ AND mhip.CRNT_REC_FLG = 1"
                                + " where la.clnt_seq=:clntSeq and la.crnt_rec_flg=1 and la.LOAN_APP_STS = 703 \r\n"
                                + " group by mhip.hlth_insr_plan_seq ")
                .setParameter("clntSeq", clntSeq);

        Object chargs;
        try {
            chargs = query.getSingleResult();
        } catch (NoResultException nre) {
            chargs = 0;
        } catch (Exception ex) {
            chargs = 0;
        }

        return FUNREAL_CHARGES - (chargs == null ? 0 : new BigDecimal(chargs.toString()).longValue());
    }

    /*
     * @modifier, Muhammad Bassam
     * @date, 26-4-2021
     * @description, multiple expense reversal applied
     * */
    //Modified by Areeba
    public boolean isExcessRecoveryPaid(Long seq, Integer incidentTyp) {
        List<MwExp> expenseList;
        if (incidentTyp == 1) {
            expenseList = mwExpRepository.findAllExcessExpByDsbltyRptSeq(seq);
        } else {
            expenseList = mwExpRepository.findAllExcessExpByDthRptSeq(seq);
        }
        Boolean expResp = false;
        for (MwExp exp : expenseList) {
            if (exp != null) {
                boolean postFlg = exp.getPostFlg() == null ? false : exp.getPostFlg();
                if (!postFlg) {
                    exp.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    exp.setLastUpdDt(Instant.now());
                    exp.setDelFlg(true);
                    exp.setCrntRecFlg(false);
                    mwExpRepository.save(exp);
                    expResp = false;
                } else expResp = true;
            }
        }
        return expResp;
    }

    public boolean isRcvryTrxForAnmlRgstrSeq(Long seq) {
        MwRcvryTrx trx = mwRcvryTrxRepository.findOneByPrntRcvryRefAndCrntRecFlg(seq, true);
        return trx == null ? false : true;
    }

    public int getClntODOrUnpostedRecvry(long clntSeq, String date) {
        Query query = em
                .createNativeQuery("SELECT\r\n" + "    COUNT(1)\r\n" + "FROM\r\n" + "    mw_pymt_sched_dtl dtl\r\n"
                        + "join mw_pymt_sched_hdr hdr on hdr.pymt_sched_hdr_seq=dtl.pymt_sched_hdr_seq and hdr.crnt_rec_flg=1\r\n"
                        + "join mw_loan_app app on app.loan_app_seq=hdr.loan_app_seq and app.crnt_rec_flg=1\r\n"
                        + "join mw_ref_cd_val val on val.ref_cd_seq=dtl.pymt_sts_key and val.crnt_rec_flg=1\r\n"
                        + "join mw_ref_cd_val stsval on stsval.ref_cd_seq=app.loan_app_sts and val.crnt_rec_flg=1\r\n"
                        + "and dtl.crnt_rec_flg=1\r\n" + "and app.clnt_seq=:clntSeq\r\n" + "and stsval.ref_cd='0005'\r\n"
                        + "and dtl.due_dt < :dthDate\r\n" + "and val.ref_cd in ('0945','1145')")
                .setParameter("clntSeq", clntSeq).setParameter("dthDate", utils.parseDatetoLocalDate(date));
        Object chargs = query.getSingleResult();

        // Added by Areeba - 22-04-2022 - OD CHECK
        Query query1 = em.createNativeQuery(" SELECT MAX (app.od_chk_flg)\n" +
                        "  FROM mw_loan_app  app\n" +
                        "       JOIN mw_clnt clnt\n" +
                        "           ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1\n" +
                        " WHERE     app.LOAN_APP_SEQ = app.PRNT_LOAN_APP_SEQ\n" +
                        "       AND app.LOAN_APP_STS = 703\n" +
                        "       AND clnt.clnt_seq = :clnt_seq\n" +
                        "       AND app.crnt_rec_flg = 1 ")
                .setParameter("clnt_seq", clntSeq);
        Object check;
        try {
            check = query1.getSingleResult();
        } catch (NoResultException nre) {
            check = null;
        }
        int check2;
        if (check == null) {
            check2 = 0;
        } else {
            check2 = new BigDecimal(check.toString()).intValue();
        }

        if (check2 == 0) {  //not checked
            return new BigDecimal(chargs.toString()).intValue();
        } else { //checked
            return 0;
        }
        // return ( chargs == null ? 0 : new BigDecimal( chargs.toString() ).intValue() );
        // Ended by Areeba
    }

    // Added by Zohaib Asim - Dated 27-08-2021 - CR: Sanction List Phase 2
    // Death Claim Restriction; if found in Tag List
    public boolean findClntTagged(Long clntSeq, Long loanAppSeq) {
        log.info("findClntTagged: clntSeq:" + clntSeq);

        Query qry = em.createNativeQuery("SELECT FN_FIND_CLNT_TAGGED('AML', " +
                clntSeq + ", " + loanAppSeq + ") FROM DUAL");
        String qryResp = qry.getSingleResult().toString();
        if (qryResp.contains("SUCCESS")) {
            return true;
        }
        return false;
    }
    // End by Zohaib Asim

    //ADDED BY YOUSAF - DEATH DATE SHOULD BE EQUAL AND GREATER THAN DISBURSEMENT DATE DATED: 28-07-2022
    //Modified by Areeba (for disability)
    public int incdntDateCheck(long clntSeq, String date) {
        Query query = em
                .createNativeQuery("SELECT count(1)\n" +
                        "  FROM MW_LOAN_APP  AP\n" +
                        "       JOIN MW_DSBMT_VCHR_HDR HDR\n" +
                        "           ON HDR.LOAN_APP_SEQ = AP.LOAN_APP_SEQ AND HDR.CRNT_REC_FLG = 1\n" +
                        " WHERE     AP.CRNT_REC_FLG = 1\n" +
                        "       AND AP.LOAN_APP_STS IN (703, 1305)       \n" +
                        "       AND AP.CLNT_SEQ = :clntSeq\n" +
                        "       AND AP.LOAN_CYCL_NUM = (SELECT MAX(LA.LOAN_CYCL_NUM) FROM MW_LOAN_APP LA WHERE LA.CLNT_SEQ = AP.CLNT_SEQ\n" +
                        "       AND LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_STS IN (703, 1305)\n" +
                        "       )\n" +
                        "       AND to_date(HDR.EFF_START_DT) >= to_date(:incdntDate)")
                .setParameter("clntSeq", clntSeq).setParameter("incdntDate", utils.parseDatetoLocalDate(date));
        Object incdntCheck = query.getSingleResult();

        if (incdntCheck.toString().equals("0")) {
            return 0;
        } else {
            return 1;
        }
    }
    // end death date check method

    // Added by Areeba - SCR - Disability Recoveries
    @Transactional
    public MwDsbltyRpt addMwDsbltyRpt(MwDsbltyRptDTO dr, String user, String token) {
        Instant now = Instant.now();
        long seq = SequenceFinder.findNextVal(Sequences.DSBLTY_RPT_SEQ);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(utils.parseStringDateToDate(dr.dtOfDsblty));

        String[] loanApps = dr.getPrntLoanAppSeq().split(",");

        for (String loanApp : loanApps
        ) {
            log.error("PrintLoanAppSeq " + loanApp);
            ResponseEntity r = serviceClient.reverseAdvanceDisabilityRecoveries(dr.clntSeq, Long.parseLong(loanApp), date, token);
        }

        MwDsbltyRpt entity = new MwDsbltyRpt();

        //get product from clnt
//        Query productQuery = em.createNativeQuery( " SELECT PRD.PRD_GRP_SEQ FROM MW_LOAN_APP LA \n" +
//                " JOIN MW_PRD PRD ON PRD.PRD_SEQ = LA.PRD_SEQ AND PRD.CRNT_REC_FLG = 1 \n" +
//                " WHERE LA.CLNT_SEQ = :clnt_seq AND LA.CRNT_REC_FLG = 1 ")
//                .setParameter("clnt_seq", dr.clntSeq);
//        List<Long> productList = productQuery.getResultList();

        //List<MwPrd> productList = mwPrdRepository.getAllPrdGrpByLoanAppSeq(dr.clntSeq);
        String query = "";

        //for(MwPrd prod: productList) {
        //calculate receivable
        query = "SELECT SUM(loan_app_ost(LOAN_APP_SEQ , to_date(:date, 'dd-MM-yyyy'), 'k') + \n" +
                "                loan_app_ost(LOAN_APP_SEQ, to_date(:date, 'dd-MM-yyyy'), 'd')) as RECEIVABLE \n" +
                "                FROM MW_LOAN_APP WHERE CLNT_SEQ = :clnt_seq AND CRNT_REC_FLG = 1 AND LOAN_APP_STS = 703";

//            if (prod.getPrdGrpSeq() == 13) {
//                query = " SELECT ((SELECT SUM(loan_app_ost(LOAN_APP_SEQ , to_date(:date, 'dd-MM-yyyy'), 'k') + \n" +
//                                " loan_app_ost(LOAN_APP_SEQ, to_date(:date, 'dd-MM-yyyy'), 'd')) as RECEIVABLE \n" +
//                                " FROM MW_LOAN_APP LA\n" +
//                                " WHERE CLNT_SEQ = :clnt_seq AND CRNT_REC_FLG = 1 AND LOAN_APP_STS = 703) /\n" +
//                                " (SELECT ABS((SELECT COUNT(*) FROM MW_PYMT_SCHED_DTL PSD WHERE PSD.PYMT_STS_KEY = 945 AND PSD.PYMT_SCHED_HDR_SEQ = PSH.PYMT_SCHED_HDR_SEQ) \n" +
//                                " ) AS REMAINING_INST\n" +
//                                " FROM MW_LOAN_APP LA \n" +
//                                " JOIN MW_PYMT_SCHED_HDR PSH ON PSH.LOAN_APP_SEQ = LA.LOAN_APP_SEQ AND PSH.CRNT_REC_FLG = 1\n" +
//                                " WHERE LA.CLNT_SEQ = :clnt_seq AND LA.CRNT_REC_FLG = 1)) AS KC\n" +
//                                " from dual";
//            }
//        }
        Query amtQuery = em.createNativeQuery(query)
                .setParameter("clnt_seq", dr.clntSeq)
                .setParameter("date", date);

        List listReceivableAmt = amtQuery.getResultList();
        long amt = Long.parseLong(listReceivableAmt.get(0).toString());

        entity.setDsbltyRptSeq(seq);
        entity.setClntSeq(dr.clntSeq);
        entity.setEffStartDt(now);
        entity.setClntNomFlg(dr.gender);
        entity.setDtOfDsblty(utils.parseStringDateToInstant(dr.dtOfDsblty));
        entity.setCrtdBy(user);
        entity.setCrtdDt(now);
        entity.setLastUpdBy(user);
        entity.setLastUpdDt(now);
        entity.setDelFlg(false);
        entity.setCrntRecFlg(true);
        entity.setAmt(amt);
        entity.setCmnt(dr.cmnt);
        entity.setInsrClmSts(-1l);
        entity.setAdjFlg(-1l);
        mwDsbltyRptRepository.save(entity);
        return entity;
    }

    public Long reverseMwDsbltyRpt(Long dsbltyRptSeq, String cmnt, String user, String token) {
        MwDsbltyRpt entity = mwDsbltyRptRepository.findOneByDsbltyRptSeqAndCrntRecFlg(dsbltyRptSeq, true);
        entity.setLastUpdBy(user);
        entity.setLastUpdDt(Instant.now());
        entity.setDelFlg(true);
        entity.setCrntRecFlg(false);
        mwDsbltyRptRepository.save(entity);

        serviceClient.reverseExcessDisabilityRecoveries(entity.getClntSeq(), token);
        return dsbltyRptSeq;
    }
    //Ended by Areeba

    /* Zohaib Asim - Dated 03-10-2022 - KSWK */
    public String collectUpFrontCashBfDeathPost(CollectUpFrontCashDto collectUpFrontCashDto,
                                                String user) {
        String resp = "";

        log.info("Cash Collection DTO", collectUpFrontCashDto.toString());

        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery(env.getProperty("spring.datasource.username")
                    + ".ADJUST_CLAIM_PYMT_SCHDL");
            storedProcedure.registerStoredProcedureParameter("P_AMT", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_CLNT", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_EXPNS_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_DIFF", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_USER", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_INCDNT_TYP", String.class, ParameterMode.IN);

            storedProcedure.setParameter("P_AMT", collectUpFrontCashDto.getRcvblAmt());
            storedProcedure.setParameter("P_CLNT", collectUpFrontCashDto.getClntId());
            storedProcedure.setParameter("P_EXPNS_SEQ", 0L);
            storedProcedure.setParameter("P_DIFF", " ");
            storedProcedure.setParameter("P_USER", user);
            storedProcedure.setParameter("P_INCDNT_TYP", "UPFRONT_CASH");
            storedProcedure.execute();

            resp = "success";
        } catch (Exception ex) {
            resp = "failed";
            ex.printStackTrace();
        }
        return resp;
    }
}
