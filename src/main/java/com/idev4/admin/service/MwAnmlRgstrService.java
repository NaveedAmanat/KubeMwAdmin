package com.idev4.admin.service;

import com.idev4.admin.domain.MwAnmlRgstr;
import com.idev4.admin.domain.MwDthRpt;
import com.idev4.admin.dto.AnmlDeathReportDto;
import com.idev4.admin.dto.AnmlListingDto;
import com.idev4.admin.feignclient.ServiceClient;
import com.idev4.admin.repository.MwAnmlRgstrRepository;
import com.idev4.admin.repository.MwDthRptRepository;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MwAnmlRgstrService {

    @Autowired
    MwDthRptRepository mwDthRptRepository;

    @Autowired
    EntityManager em;

    @Autowired
    Utils utils;

    @Autowired
    ServiceClient serviceClient;

    @Autowired
    MwAnmlRgstrRepository mwAnmlRgstrRepository;

    public List<AnmlListingDto> getAllAnml(long clntSeq) {

        Query anmlsListQury = em.createNativeQuery(
                        "select ar.anml_rgstr_seq,ar.rgstr_cd,ar.tag_num,null,typ.ref_cd_dscr typ,ar.ANML_BRD,ar.prch_dt, ar.age_yr, ar.age_mnth, ar.prch_amt,dr.dt_of_dth,dr.cause_of_dth, ar.ANML_STS, dr.CLNT_NOM_FLG , dr.INSR_CLM_STS, ar.CRTD_DT\r\n"
                                + "from mw_anml_rgstr ar\r\n" // + "join mw_ref_cd_val knd on knd.ref_cd_seq=ar.anml_knd and knd.crnt_rec_flg=1\r\n"
                                + "join mw_ref_cd_val typ on typ.ref_cd_seq=ar.anml_typ and typ.crnt_rec_flg=1\r\n"
                                + "join mw_loan_app la on la.loan_app_seq = ar.loan_app_seq and la.crnt_rec_flg = 1 and la.loan_app_sts=703\r\n"
                                + "left outer join mw_dth_rpt dr on dr.clnt_seq =ar.anml_rgstr_seq and (dr.clnt_nom_flg=3 or dr.clnt_nom_flg=4) and dr.CRNT_REC_FLG = 1\r\n"
                                + "where ar.crnt_rec_flg = 1 and la.clnt_seq=:clntSeq")
                .setParameter("clntSeq", clntSeq);

        List<Object[]> anmlList = anmlsListQury.getResultList();
        List<AnmlListingDto> listing = new ArrayList<AnmlListingDto>();
        anmlList.forEach(a -> {
            AnmlListingDto dto = new AnmlListingDto();
            dto.anmlRgstrSeq = a[0] == null ? "0" : a[0].toString();

            dto.rgstrCd = a[1] == null ? "" : a[1].toString();

            dto.tagNum = a[2] == null ? "" : a[2].toString();

            dto.anmlKnd = a[3] == null ? "" : a[3].toString();

            dto.anmlTyp = a[4] == null ? "" : a[4].toString();

            dto.anmlBrd = a[5] == null ? "" : a[5].toString();

            dto.prchDt = a[6] == null ? "" : a[6].toString();

            dto.ageYr = a[7] == null ? 0 : new BigDecimal(a[7].toString()).longValue();

            dto.ageMnth = a[8] == null ? 0 : new BigDecimal(a[8].toString()).longValue();

            dto.prchAmt = a[9] == null ? 0 : new BigDecimal(a[9].toString()).longValue();

            dto.dthDt = a[10] == null ? "" : a[10].toString();

            dto.dthCase = a[11] == null ? "" : a[11].toString();

            dto.anmlSts = a[12] == null ? "" : a[12].toString();

            dto.clntNomFlg = a[13] == null ? "" : a[13].toString();

            dto.clmSts = a[14] == null ? "" : a[14].toString();

            dto.rgstrDt = a[15] == null ? "" : a[15].toString();
            listing.add(dto);
        });

        return listing;

    }

    public MwAnmlRgstr addMwDthRpt(AnmlDeathReportDto dr, String user) {
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dr.anmlRgstrSeq, true);
        if (dr.type == 3 || dr.type == 4) {
            MwDthRpt entity = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(dr.anmlRgstrSeq, true);
            if (entity == null) {
                long seq = SequenceFinder.findNextVal(Sequences.DTH_RPT_SEQ);
                entity = new MwDthRpt();
                entity.setDthRptSeq(seq);
            }

            Instant now = Instant.now();
            entity.setClntSeq(dr.anmlRgstrSeq);
            entity.setEffStartDt(now);
            entity.setClntNomFlg(dr.type);
            entity.setDtOfDth(utils.parseStringDateToInstant(dr.dthDt));
            entity.setCauseOfDth(dr.dthCase);
            entity.setDthCertNum(null);
            entity.setCrtdBy(user);
            entity.setCrtdDt(now);
            entity.setLastUpdBy(user);
            entity.setLastUpdDt(now);
            entity.setDelFlg(false);
            entity.setCrntRecFlg(true);
            entity.setAmt(0L);
            mwDthRptRepository.save(entity);
        }
        rgstr.setAnmlSts(new Long(dr.type));
        return mwAnmlRgstrRepository.save(rgstr);
    }

    public Long reverseMwDthRpt(long anmlRgstrSeq, String cmnt, String user) {
        Instant now = Instant.now();
        MwDthRpt entity = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(anmlRgstrSeq, true);
        entity.setLastUpdBy(user);
        entity.setLastUpdDt(now);
        entity.setDelFlg(true);
        entity.setCrntRecFlg(false);
        mwDthRptRepository.delete(entity);
        return anmlRgstrSeq;

    }

    public List anmlInsrClm(String curUser) {
        Query query = em.createNativeQuery(
                        "select rpt.dth_rpt_seq, rpt.clnt_nom_flg, rpt.dt_of_dth, rpt.cause_of_dth, rpt.dth_cert_num, rpt.amt, rpt.insr_clm_sts, rpt.insr_clm_rmrks, rgstr.anml_rgstr_seq, rgstr.tag_num, clnt.clnt_id, clnt.frst_nm, clnt.last_nm, clnt.cnic_num from mw_dth_rpt rpt \r\n"
                                + "join mw_anml_rgstr rgstr on rgstr.ANML_RGSTR_SEQ=rpt.clnt_seq and  rgstr.crnt_rec_flg=1\r\n"
                                + "join mw_loan_app app on app.loan_app_seq = rgstr.loan_app_seq and app.crnt_rec_flg=1\r\n"
                                + "join mw_clnt clnt on clnt.clnt_seq = app.clnt_seq and clnt.crnt_rec_flg=1\r\n"
                                + "join mw_acl acl on acl.port_seq=app.port_seq and acl.user_id=:userId\r\n"
                                + "where rpt.CLNT_NOM_FLG In (3,4) and rpt.crnt_rec_flg=1")
                .setParameter("userId", curUser);
        List<Object[]> queryResult = query.getResultList();
        List<Map<String, ?>> anmlInsrClmList = new ArrayList();
        queryResult.forEach(w -> {
            Map<String, Object> parm = new HashMap<>();
            parm.put("dthRptSeq", w[0] == null ? "" : w[0].toString());
            parm.put("clntNomFlg", w[1] == null ? "" : w[1].toString());
            parm.put("dtOfDth", w[2] == null ? "" : w[2].toString());
            parm.put("causeOfDth", w[3] == null ? "" : w[3].toString());
            parm.put("causeOfDth", w[4] == null ? "" : w[4].toString());
            parm.put("amt", w[5] == null ? "" : w[5].toString());
            parm.put("sts", w[6] == null ? "" : w[6].toString());
            parm.put("rmrks", w[7] == null ? "" : w[7].toString());
            parm.put("anmlRgstrSeq", w[8] == null ? "" : w[8].toString());
            parm.put("tagNum", w[9] == null ? "" : w[9].toString());
            parm.put("clntId", w[10] == null ? "" : w[10].toString());
            parm.put("frstNm", w[11] == null ? "" : w[11].toString());
            parm.put("lastNm", w[12] == null ? "" : w[12].toString());
            parm.put("cnicNum", w[13] == null ? "" : w[13].toString());
            anmlInsrClmList.add(parm);
        });
        return anmlInsrClmList;
    }

    @Transactional
    public MwAnmlRgstr markAnmlInsrSts(long dthRgstrSeq, long stsKey, String user) {
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dthRgstrSeq, true);
        if (stsKey == 3 || stsKey == 4) {
            MwDthRpt rpt = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(dthRgstrSeq, true);
            if (rpt == null) {
                return null;
            }
            rpt.setEffStartDt(Instant.now());
            rpt.setInsrClmSts(0L);
            rpt.setLastUpdBy(user);
            rpt.setLastUpdDt(Instant.now());
            mwDthRptRepository.save(rpt);
        }
        rgstr.setAnmlSts(stsKey);
        return mwAnmlRgstrRepository.save(rgstr);
    }

    @Transactional
    public MwAnmlRgstr markAnmlInsrAdj(long dthRgstrSeq, long stsKey, String user, Long amt, String token) {
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dthRgstrSeq, true);
        if (stsKey == 3 || stsKey == 4) {
            MwDthRpt rpt = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(dthRgstrSeq, true);
            if (rpt == null)
                return null;
            rpt.setEffStartDt(Instant.now());
            rpt.setInsrClmSts(stsKey);
            rpt.setLastUpdBy(user);
            rpt.setLastUpdDt(Instant.now());
            serviceClient.adjustAnmlInsClaim(rgstr.getLoanAppSeq(), amt, rgstr.getAnmlRgstrSeq(), token);
            mwDthRptRepository.save(rpt);

        }
        return rgstr;
    }

    public MwAnmlRgstr markAnmlInsrStsRvrt(long dthRgstrSeq, String user) {
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dthRgstrSeq, true);
        MwDthRpt exRpt = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(dthRgstrSeq, true);
        rgstr.setAnmlSts(Long.valueOf(exRpt.getClntNomFlg()));
        return mwAnmlRgstrRepository.save(rgstr);
    }

    public MwAnmlRgstr revertAnmlDeath(long dthRgstrSeq, String user) {
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dthRgstrSeq, true);
        MwDthRpt exRpt = mwDthRptRepository.findOneByClntSeqAndCrntRecFlg(dthRgstrSeq, true);
        if (exRpt != null) {
            exRpt.setCrntRecFlg(false);
            exRpt.setDelFlg(true);
            exRpt.setLastUpdBy(user);
            exRpt.setLastUpdDt(Instant.now());
            mwDthRptRepository.save(exRpt);
        }
        rgstr.setAnmlSts(-1L);
        return mwAnmlRgstrRepository.save(rgstr);
    }

    //Added by Areeba
    public Boolean checkAnmlAdj(Long seq, Long amount) {
        Long loanAppSeq = mwAnmlRgstrRepository.findAnmlLoanAppByClntSeq(seq);
        if (loanAppSeq != null) {
            Query query1 = em.createNativeQuery(
                            " SELECT NVL((SELECT PSD.PPAL_AMT_DUE + PSD.TOT_CHRG_DUE FROM MW_PYMT_SCHED_DTL PSD\n" +
                                    " JOIN MW_PYMT_SCHED_HDR PSH ON PSD.PYMT_SCHED_HDR_SEQ = PSH.PYMT_SCHED_HDR_SEQ AND PSH.CRNT_REC_FLG = 1\n" +
                                    " JOIN MW_LOAN_APP LA ON PSH.LOAN_APP_SEQ = LA.LOAN_APP_SEQ AND LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_SEQ = :SEQ\n" +
                                    " WHERE PSD.PYMT_STS_KEY = 945 AND PSD.CRNT_REC_FLG = 1\n" +
                                    " ORDER BY PSD.DUE_DT DESC\n" +
                                    " FETCH FIRST 1 ROWS ONLY),0) from dual ")
                    .setParameter("SEQ", loanAppSeq);
            List query1Result = query1.getResultList();
            long lastInstAmt = Long.parseLong(query1Result.get(0).toString());

            Query query2 = em.createNativeQuery(
                            " SELECT NVL((SELECT (SELECT DVD.AMT FROM MW_DSBMT_VCHR_DTL DVD\n" +
                                    " JOIN MW_DSBMT_VCHR_HDR DVH ON DVD.DSBMT_HDR_SEQ = DVH.DSBMT_HDR_SEQ AND DVH.CRNT_REC_FLG = 1\n" +
                                    " JOIN MW_LOAN_APP LA ON DVH.LOAN_APP_SEQ = LA.LOAN_APP_SEQ AND LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_SEQ = :SEQ\n" +
                                    " WHERE DVD.CRNT_REC_FLG = 1) + \n" +
                                    " (SELECT SUM(PSD.TOT_CHRG_DUE) FROM MW_PYMT_SCHED_DTL PSD\n" +
                                    " JOIN MW_PYMT_SCHED_HDR PSH ON PSD.PYMT_SCHED_HDR_SEQ = PSH.PYMT_SCHED_HDR_SEQ AND PSH.CRNT_REC_FLG = 1\n" +
                                    " JOIN MW_LOAN_APP LA ON PSH.LOAN_APP_SEQ = LA.LOAN_APP_SEQ AND LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_SEQ = :SEQ\n" +
                                    " WHERE PSD.CRNT_REC_FLG = 1) AS SRVC_CHRGS\n" +
                                    " FROM DUAL),0) FROM DUAL ")
                    .setParameter("SEQ", loanAppSeq);
            List query2Result = query2.getResultList();
            long totalAmt = Long.parseLong(query2Result.get(0).toString());
            if (lastInstAmt == 0 || totalAmt == 0) {
                return null;
            } else if (amount < lastInstAmt) {
                return false;
            } else if (amount > totalAmt) {
                return false;
            } else
                return true;
        } else
            return null;
    }
    //Ended by Areeba
}
