package com.idev4.admin.service;

import com.idev4.admin.domain.MwDnrTgng;
import com.idev4.admin.domain.MwLoanApp;
import com.idev4.admin.dto.DistBrnchDto;
import com.idev4.admin.dto.DonnerUploaderDto;
import com.idev4.admin.dto.DonorClientTaggingDTO;
import com.idev4.admin.dto.DonorTaggingForm;
import com.idev4.admin.repository.MwDnrTgngRepository;
import com.idev4.admin.repository.MwLoanAppRepository;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DonorTaggingService {

    @Autowired
    EntityManager em;

    @Autowired
    MwLoanAppRepository mwLoanAppRepository;

    @Autowired
    MwDnrTgngRepository mwDnrTgngRepository;

    // CR-Donor Tagging
    // Add Filter for LoanAppSeq
    // tagged multiple clients at once
    // tagged clients using uploader
    // show invalid loanAppSeq(inactive, already tagged and not found in system)
    // Added By Naveed - 20-12-2021
    public Map<String, String> getDonorTaggingFiltterdList(DonorTaggingForm from) {

        Map<String, Object> paramaterMap = new HashMap<String, Object>();
        List<String> whereCause = new ArrayList<String>();

        StringBuilder query = new StringBuilder();

        query.append(" from (select app.loan_app_seq,clnt_id,        clnt.frst_nm||' '||clnt.last_nm,\r\n"
                + "        		        		                       app.aprvd_loan_amt,        vh.dsbmt_dt,        brnch.brnch_nm,        dst.dist_nm,\r\n"
                + "        		        		                       dst.dist_seq,        sct.biz_sect_nm,        sct.biz_sect_seq,\r\n"
                + "        		        		                       acty.biz_acty_nm,        acty.biz_acty_seq,        app.loan_cycl_num,\r\n"
                + "        		        		                       mp.prd_seq, mp.prd_grp_seq, mp.PRD_NM, mp.PRD_CMNT\r\n"
                + "        		        		                       , SUM(app.aprvd_loan_amt) OVER(ORDER BY app.eff_start_dt Desc ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) running_total\r\n"
                + "        		        		                from mw_clnt clnt join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and loan_app_sts=703 \r\n"
                + "        		        		                join mw_port prt on prt.port_seq=app.port_seq  join mw_brnch brnch on brnch.brnch_seq=prt.brnch_seq \r\n"
                + "        		        		                join mw_dsbmt_vchr_hdr vh on vh.loan_app_seq=app.loan_app_seq and vh.dsbmt_vchr_typ=0 and vh.crnt_rec_flg = 1 \r\n"
                + "        		        		                left outer join mw_biz_aprsl aprsl on aprsl.loan_app_seq=app.loan_app_seq \r\n"
                + "        		        		                left outer join mw_biz_acty acty on acty.biz_acty_seq=aprsl.acty_key \r\n"
                + "        		        		                left outer join mw_biz_sect sct on sct.biz_sect_seq=acty.biz_sect_seq \r\n"
                + "        		        		                join mw_addr_rel arl on arl.enty_key=clnt.clnt_seq and arl.enty_typ='Client' \r\n"
                + "        		        		                join mw_addr adr on adr.addr_seq=arl.addr_seq \r\n"
                + "        		        		                join mw_city_uc_rel ucrl on ucrl.city_uc_rel_seq=adr.city_seq  join mw_uc uc on uc.uc_seq=ucrl.uc_seq \r\n"
                + "        		        		                join mw_thsl thsl on thsl.thsl_seq=uc.thsl_seq  join mw_dist dst on dst.dist_seq=thsl.dist_seq \r\n"
                + "        		        		                join mw_prd mp on mp.prd_seq=app.prd_seq and mp.crnt_rec_flg=1\r\n"
                + "        		                                join mw_ref_cd_val prdTypVal on prdTypVal.ref_cd_seq=mp.prd_typ_key and prdTypVal.crnt_rec_flg=1 and prdTypVal.ref_cd!='1165'\r\n"
                + "        		        		                where nvl(app.dnr_seq,0)=0 %REPLACE%  )\r\n"
                + "        		        		                where running_total<=:fundAmt");

        StringBuilder whereClause = new StringBuilder();

        if (from.clntSeq != null) {
            whereClause.append(" and clnt.clnt_seq = :clntSeq ");
            paramaterMap.put("clntSeq", from.clntSeq);
        } else if (from.loanAppSeq != null) {
            whereClause.append(" and app.LOAN_APP_SEQ = :loanSeq ");
            paramaterMap.put("loanSeq", from.loanAppSeq);
        } else {
            if (from.frmAmt != null && from.toAmt != null) {
                whereClause.append(" and app.aprvd_loan_amt between :frmLoanAmt and :toLoanAmt ");
                paramaterMap.put("frmLoanAmt", from.frmAmt);
                paramaterMap.put("toLoanAmt", from.toAmt);
            }

            if (from.cycle != null) {
                whereClause.append(" and app.loan_cycl_num =:cyclNum ");
                paramaterMap.put("cyclNum", from.cycle);
            }

            if (from.frmDt != null && !from.frmDt.isEmpty() && from.toDt != null && !from.toDt.isEmpty()) {
                whereClause.append(" and to_date(vh.dsbmt_dt) between to_date(:frmDsbmtDt,'dd-mm-yyyy') and to_date(:toDsbmtDt,'dd-mm-yyyy') ");
                paramaterMap.put("frmDsbmtDt", from.frmDt);
                paramaterMap.put("toDsbmtDt", from.toDt);
            }

            if (from.branchs != null && from.branchs.size() > 0) {
            /* List< Long > convertedBranchList = Stream.of( from.branchs.split( "," ) ).map( String::trim ).map( Long::parseLong )
                    .collect( Collectors.toList() );*/
                whereClause.append(" and brnch.brnch_seq in (:brnchSeq) ");
                paramaterMap.put("brnchSeq", from.branchs);
            }

            if (from.districts != null && from.districts.size() > 0) {
                whereClause.append(" and dst.dist_seq in (:dist) ");
                paramaterMap.put("dist", from.districts);
            }

            if (from.sectors != null && from.sectors.size() > 0) {
                whereClause.append(" and sct.biz_sect_seq in (:bizSct) ");
                paramaterMap.put("bizSct", from.sectors);
            }

            if (from.activities != null && from.activities.size() > 0) {
                whereClause.append(" and acty.biz_acty_seq in (:acty) ");
                paramaterMap.put("acty", from.activities);
            }

            if (from.prds != null && from.prds.size() > 0) {
                whereClause.append(" and app.prd_seq in (:prds) ");
                paramaterMap.put("prds", from.prds);
            }

            if (from.prd_grps != null && from.prd_grps.size() > 0) {
                whereClause.append(" and mp.prd_grp_seq in (:grps) ");
                paramaterMap.put("grps", from.prd_grps);
            }
        }
        paramaterMap.put("fundAmt", from.fundAmt);

        if (from.uploader) {
            query.insert(0, "SELECT loan_app_seq ");
        } else {
            query.insert(0, "SELECT COUNT(loan_app_seq) as clnts, SUM(aprvd_loan_amt) as aprvd_loan_amt, :fundAmt fund_Amt ");
        }

        Query donorQury = em.createNativeQuery(query.toString().replace("%REPLACE%", whereClause.toString()));

        for (String key : paramaterMap.keySet()) {
            donorQury.setParameter(key, paramaterMap.get(key));
        }

        List<Object[]> donorList = new ArrayList<>();
        Map<String, String> resp = new HashMap<>();
        if (from.uploader) {
            donorList = donorQury.getResultList();

            DonorClientTaggingDTO dto = new DonorClientTaggingDTO();
            dto.dnrSeq = from.funder;
            dto.fundAmt = from.fundAmt;

            for (Object loan : donorList) {
                dto.loanAppSeq.add(loan == null ? 0 : new BigDecimal(loan.toString()).longValue());
            }
            resp.putAll(TagDonorClients(dto));
        } else {
            donorList = donorQury.getResultList();
            donorList.forEach(donor -> {
                resp.put("clients", donor[0] == null ? "0" : donor[0].toString());
                resp.put("approvedAmt", donor[1] == null ? "0" : donor[1].toString());
                resp.put("fundsAmt", donor[2] == null ? "0" : donor[2].toString());
            });
        }
        return resp;
    }

    public Map<String, String> TagDonorClients(DonorClientTaggingDTO dto) {
        final int batchSize = 500;
        List<Long> loansList = new ArrayList();
        Map<String, String> resp = new HashMap<>();

        for (int i = 0; i < dto.loanAppSeq.size(); i++) {
            loansList.add(dto.loanAppSeq.get(i));
            if (i % batchSize == 0 && i > 0) {
                saveDonorTag(loansList, dto.dnrSeq);
                loansList.clear();
            }
        }
        if (loansList.size() > 0) {
            saveDonorTag(loansList, dto.dnrSeq);
            loansList.clear();
        }
        resp.put("success", "Loan Donor List Updated.");
        return resp;
    }

    private void saveDonorTag(List<Long> loansList, long dnrSeq) {
        List<MwLoanApp> loans = mwLoanAppRepository.findAllLoansForTagging(loansList);
        List<MwDnrTgng> mwDnrTgngList = new ArrayList<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        for (MwLoanApp loan : loans) {
            loan.setDnrSeq((int) dnrSeq);
            loan.setLastUpdBy(currUser);
            mwDnrTgngList.add(dnrTgng(dnrSeq, loan.getLoanAppSeq(),
                    loan.getAprvdLoanAmt().longValue(), currUser));
        }
        mwLoanAppRepository.save(loans);
        mwDnrTgngRepository.save(mwDnrTgngList);
    }


    public ResponseEntity TagDonorClientsUploader(List<DonnerUploaderDto> loanLists, String currUser) {
        List<MwLoanApp> loansList = new ArrayList<>();
        List<MwDnrTgng> mwDnrTgngList = new ArrayList<>();
        Map<String, Object> resp = new HashMap<>();
        List<Long> invalidLoans = new ArrayList<>();

        final int batchSize = 500;

        for (int i = 0; i < loanLists.size(); i++) {
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeq(loanLists.get(i).loanAppSeq);
            if (app == null) {
                invalidLoans.add(loanLists.get(i).loanAppSeq);
                continue;
            }
            app.setDnrSeq(loanLists.get(i).dnrSeq);
            app.setLastUpdBy(currUser);
            app.setLastUpdDt(Instant.now());
            loansList.add(app);

            mwDnrTgngList.add(dnrTgng(loanLists.get(i).dnrSeq, app.getLoanAppSeq(),
                    app.getAprvdLoanAmt().longValue(), currUser));

            if (i % batchSize == 0 && i > 0) {
                mwLoanAppRepository.save(loansList);
                mwDnrTgngRepository.save(mwDnrTgngList);
                loansList.clear();
            }
        }
        if (loansList.size() > 0) {
            mwLoanAppRepository.save(loansList);
            mwDnrTgngRepository.save(mwDnrTgngList);
            loansList.clear();
        }
        resp.put("success", "Loan Donor List Updated.");
        resp.put("invalid", invalidLoans);
        return ResponseEntity.ok().body(resp);
    }

    private MwDnrTgng dnrTgng(long dnrSeq, long appSeq, long amount, String user) {

        MwDnrTgng mwDnrTgng = new MwDnrTgng();
        long seq = SequenceFinder.findNextVal(Sequences.DNR_TGNG_SEQ);
        mwDnrTgng.setDnrTgngSeq(seq);
        mwDnrTgng.setCrntRecFlg(true);
        mwDnrTgng.setCrtdBy(user);
        mwDnrTgng.setCrtdDt(Instant.now());
        mwDnrTgng.setDelFlg(false);
        mwDnrTgng.setDnrSeq(dnrSeq);
        mwDnrTgng.setEffStartDt(Instant.now());
        mwDnrTgng.setLastUpdBy(user);
        mwDnrTgng.setLastUpdDt(Instant.now());
        mwDnrTgng.setTagAmt(amount);
        mwDnrTgng.setTagClnts(appSeq);
        mwDnrTgng.setTotTagAmt(amount);

        return mwDnrTgng;
    }

    public ResponseEntity getDistBranchDetail() {
        List<Object[]> lists = mwDnrTgngRepository.findAllDistrictByBranch();
        List<DistBrnchDto> dtoList = new ArrayList<>();
        lists.forEach(l -> {
            DistBrnchDto dto = new DistBrnchDto();
            dto.distSeq = l[0] == null ? 0 : new BigDecimal(l[0].toString()).longValue();
            dto.distName = l[1] == null ? "" : l[1].toString();
            dto.brnchSeq = l[2] == null ? 0 : new BigDecimal(l[2].toString()).longValue();
            dto.brnchNm = l[3] == null ? "" : l[3].toString();

            dtoList.add(dto);
        });
        return ResponseEntity.ok().body(dtoList);
    }

    // Ended By Naveed - 20-12-2021
}
