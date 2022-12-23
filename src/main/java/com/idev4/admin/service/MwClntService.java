package com.idev4.admin.service;

import com.idev4.admin.domain.MwLoanApp;
import com.idev4.admin.dto.BypassOdDto;
import com.idev4.admin.dto.LoanServingDTO;
import com.idev4.admin.repository.ClientRepository;
import com.idev4.admin.repository.MwLoanAppRepository;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MwClntService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MwLoanAppRepository mwLoanAppRepository;

    @Autowired
    EntityManager em;

    // public List< LoanServingDTO > getAllActiveClint( String user, Long brnchSeq ) {
    // Query query = em.createNativeQuery( "SELECT clnt_id, clnt_seq, frst_nm, last_nm, \r\n"
    // + " SUM (ppal_amt_due), SUM (tot_chrg_due), NVL(SUM(rcvd_amt),0), dth_rpt_seq, \r\n"
    // + " MAX (amt), brnch_seq, max(exp_seq) exp_seq,-- LISTAGG (to_char(exp_seq), ',') WITHIN GROUP (ORDER BY clnt_seq) exp_seq, \r\n"
    // + " LISTAGG (prd_cmnt, ',') WITHIN GROUP (ORDER BY clnt_seq) prd_cmnt, \r\n"
    // + " LISTAGG (loan_app_sts_dt, ',') WITHIN GROUP (ORDER BY clnt_seq) loan_app_sts_dt, rel_typ_flg, \r\n"
    // + " post_flg, LISTAGG (prd_id, ',') WITHIN GROUP (ORDER BY clnt_seq) prd_id FROM ( SELECT \r\n"
    // + " clnt.clnt_id, clnt.clnt_seq, clnt.frst_nm, clnt.last_nm, \r\n"
    // + " SUM (ppal_amt_due) ppal_amt_due, SUM (tot_chrg_due) tot_chrg_due, MAX (rcvry_amt) rcvd_amt, \r\n"
    // + " dr.dth_rpt_seq, MAX (dr.amt) amt, brnch.brnch_seq, e.exp_seq, prd_cmnt, \r\n"
    // + " TRUNC (hdr.DSBMT_DT) loan_app_sts_dt, rel.rel_typ_flg, e.post_flg, p.prd_id \r\n"
    // + " FROM mw_clnt clnt \r\n"
    // + " JOIN mw_loan_app app ON app.clnt_seq = clnt.clnt_seq AND app.crnt_rec_flg = 1 --and app.prd_seq <> 29\r\n"
    // + " join MW_DSBMT_VCHR_HDR hdr on hdr.LOAN_APP_SEQ=app.LOAN_APP_SEQ and hdr.CRNT_REC_FLG=1 and hdr.DSBMT_VCHR_TYP=0\r\n"
    // + " JOIN mw_ref_cd_val val ON val.ref_cd_seq = app.loan_app_sts AND val.crnt_rec_flg = 1 AND val.del_flg = 0 AND val.ref_cd = '0005'
    // \r\n"
    // + " JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id =:user \r\n"
    // + " JOIN mw_pymt_sched_hdr psh ON app.loan_app_seq = psh.loan_app_seq AND psh.crnt_rec_flg = 1 \r\n"
    // + " JOIN mw_pymt_sched_dtl psd ON psh.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq AND psd.crnt_rec_flg = 1 \r\n"
    // + " JOIN mw_prd p ON p.prd_seq = app.prd_seq AND p.crnt_rec_flg = 1 \r\n"
    // + " JOIN mw_port port ON port.port_seq = clnt.port_key AND port.crnt_rec_flg = 1 \r\n"
    // + " JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 LEFT OUTER JOIN ( \r\n"
    // + " SELECT psd.pymt_sched_hdr_seq,SUM(rd.PYMT_AMT) rcvry_amt FROM mw_rcvry_dtl rd \r\n"
    // + " JOIN mw_pymt_sched_dtl psd ON rd.PYMT_SCHED_DTL_SEQ=psd.PYMT_SCHED_DTL_SEQ AND psd.crnt_rec_flg = 1 \r\n"
    // + " where rd.CHRG_TYP_KEY in (-1,(select t.TYP_SEQ from mw_typs t where t.TYP_SEQ=rd.CHRG_TYP_KEY and t.TYP_ID='0017' and
    // t.CRNT_REC_FLG = 1)) \r\n"
    // + " and rd.crnt_rec_flg = 1 group by pymt_sched_hdr_seq \r\n"
    // + " ) rcvry ON rcvry.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq LEFT OUTER JOIN mw_dth_rpt dr \r\n"
    // + " ON dr.clnt_seq = clnt.clnt_seq AND dr.crnt_rec_flg = 1 AND dr.crtd_dt > app.crtd_dt \r\n"
    // + " LEFT OUTER JOIN mw_exp e ON e.exp_ref = clnt.clnt_seq AND e.crnt_rec_flg = 1 and e.DEL_FLG = 0 AND e.CRTD_DT > app.CRTD_DT and
    // e.EXPNS_TYP_SEQ=(select TYP_SEQ from mw_typs where CRNT_REC_FLG = 1 and typ_id='0424') \r\n"
    // + " LEFT OUTER JOIN mw_clnt_rel rel ON rel.loan_app_seq = app.prnt_loan_app_seq AND rel.crnt_rec_flg = 1 AND rel.rel_typ_flg = 1
    // \r\n"
    // + " WHERE clnt.crnt_rec_flg = 1 and brnch.brnch_seq=:brnchSeq GROUP BY clnt.clnt_id, clnt.clnt_seq, clnt.frst_nm, \r\n"
    // + " clnt.last_nm, dr.dth_rpt_seq, brnch.brnch_seq, e.exp_seq, prd_cmnt, \r\n"
    // + " TRUNC (hdr.DSBMT_DT), rel.rel_typ_flg, e.post_flg, p.prd_id ) \r\n"
    // + " GROUP BY clnt_id, clnt_seq, frst_nm, last_nm, dth_rpt_seq, brnch_seq,rel_typ_flg, post_flg" )
    // .setParameter( "user", user ).setParameter( "brnchSeq", brnchSeq ).setHint( QueryHints.CACHEABLE, true );
    // List< Object[] > clnts = query.getResultList();
    //
    // List< LoanServingDTO > dtoList = new ArrayList();
    // clnts.forEach( c -> {
    // LoanServingDTO dto = new LoanServingDTO();
    // dto.clntId = c[ 0 ].toString();
    // dto.clntSeq = c[ 1 ].toString();
    // dto.frstNm = c[ 2 ] == null ? "" : c[ 2 ].toString();
    // dto.lastNm = c[ 3 ] == null ? "" : c[ 3 ].toString();
    // dto.loanAmt = c[ 4 ] == null ? 0 : new BigDecimal( c[ 4 ].toString() ).longValue();
    // dto.sercvChrgs = c[ 5 ] == null ? 0 : new BigDecimal( c[ 5 ].toString() ).longValue();
    // dto.rcvdAmt = c[ 6 ] == null ? 0 : new BigDecimal( c[ 6 ].toString() ).longValue();
    // dto.dthRptSeq = c[ 7 ] == null ? 0 : new BigDecimal( c[ 7 ].toString() ).longValue();
    // dto.amt = c[ 8 ] == null ? 0 : new BigDecimal( c[ 8 ].toString() ).longValue();
    // dto.brnchSeq = c[ 9 ] == null ? 0 : new BigDecimal( c[ 9 ].toString() ).longValue();
    // dto.paid = c[ 10 ] == null ? false : true;
    // dto.expSeq = c[ 10 ] == null ? "" : c[ 10 ].toString();
    // // if ( c[ 10 ] != null ) {
    // // List< Long > list = new ArrayList< Long >();
    // // for ( String s : c[ 10 ].toString().split( "," ) )
    // // list.add( Long.parseLong( s ) );
    // // }
    // // dto.expList = list;
    // dto.prd = c[ 11 ] == null ? "" : c[ 11 ].toString();
    // dto.disDate = c[ 12 ] == null ? "" : ( c[ 12 ].toString().split( ",", 1 ) )[ 0 ];
    // dto.relTypFlg = c[ 13 ] == null ? 0 : new BigDecimal( c[ 13 ].toString() ).longValue();
    // dto.post = c[ 14 ] == null ? false : true;
    // dto.prdId = c[ 15 ] == null ? "" : c[ 15 ].toString();
    // dtoList.add( dto );
    // } );
    // return dtoList;
    //
    // }

    public Map<String, Object> getAllActiveClint(String userId, Long brnchSeq, Integer pageIndex, Integer pageSize, String filter,
                                                 Boolean isCount) {

        // Modified by Areeba

        // Modified by Yousaf Dated: 26-09-2022
        String activeClntScript = "SELECT clnt_id,\n" +
                "         clnt_seq,\n" +
                "         frst_nm,\n" +
                "         last_nm,\n" +
                "         SUM (ppal_amt_due),\n" +
                "         SUM (tot_chrg_due),\n" +
                "         NVL (SUM (rcvd_amt), 0),\n" +
                "         dth_rpt_seq,\n" +
                "         MAX (amt),\n" +
                "         dsblty_rpt_seq,         \n" +
                "         MAX (dsblty_amt),         \n" +
                "         brnch_seq,\n" +
                "         MAX (exp_seq)\n" +
                "             exp_seq, -- LISTAGG (to_char(exp_seq), ',') WITHIN GROUP (ORDER BY clnt_seq) exp_seq,\n" +
                "         LISTAGG (prd_cmnt, ',') WITHIN GROUP (ORDER BY clnt_seq)\n" +
                "             prd_cmnt,\n" +
                "         LISTAGG (loan_app_sts_dt, ',') WITHIN GROUP (ORDER BY clnt_seq)\n" +
                "             loan_app_sts_dt,\n" +
                "         MAX (rel_typ_flg)\n" +
                "             rel_typ_flg,\n" +
                "         post_flg,\n" +
                "         LISTAGG (prd_id, ',') WITHIN GROUP (ORDER BY clnt_seq)\n" +
                "             prd_id,\n" +
                "         LISTAGG (TO_CHAR (prnt_loan_app_seq), ',')\n" +
                "             WITHIN GROUP (ORDER BY clnt_seq)\n" +
                "             prnt_loan_app_seq,\n" +
                "         crnt_rec_flg,\n" +
                "         ANML_RGSTR_SEQ,\n" +
                "         MAX (anml_prchd_amt)\n" +
                "             anml_prchd_amt\n" +
                "    FROM (  SELECT clnt.clnt_id,\n" +
                "                   clnt.clnt_seq,\n" +
                "                   clnt.frst_nm,\n" +
                "                   clnt.last_nm,\n" +
                "                   SUM (ppal_amt_due)       ppal_amt_due,\n" +
                "                   SUM (tot_chrg_due)       tot_chrg_due,\n" +
                "                   MAX (rcvry_amt)          rcvd_amt,\n" +
                "                   dr.dth_rpt_seq,\n" +
                "                   MAX (dr.amt)             amt,\n" +
                "                   mdr.dsblty_rpt_seq,\n" +
                "                   MAX (mdr.amt)            dsblty_amt,\n" +
                "                   anm.ANML_RGSTR_SEQ,\n" +
                "                   MAX (anm.PRCH_AMT)       anml_prchd_amt,\n" +
                "                   brnch.brnch_seq,\n" +
                "                   e.exp_seq,\n" +
                "                   prd_cmnt,\n" +
                "                   TRUNC (hdr.DSBMT_DT)     loan_app_sts_dt,\n" +
                "                   rel.rel_typ_flg,\n" +
                "                   e.post_flg,\n" +
                "                   p.prd_id,\n" +
                "                   app.prnt_loan_app_seq,\n" +
                "                   mrt.crnt_rec_flg\n" +
                "              FROM mw_clnt clnt\n" +
                "                   JOIN mw_loan_app app\n" +
                "                       ON app.clnt_seq = clnt.clnt_seq AND app.crnt_rec_flg = 1 --and app.prd_seq <> 29\n" +
                "                   JOIN MW_DSBMT_VCHR_HDR hdr\n" +
                "                       ON     hdr.LOAN_APP_SEQ = app.LOAN_APP_SEQ\n" +
                "                          AND hdr.CRNT_REC_FLG = 1\n" +
                "                          AND hdr.DSBMT_VCHR_TYP = 0\n" +
                "                   JOIN mw_ref_cd_val val\n" +
                "                       ON     val.ref_cd_seq = app.loan_app_sts\n" +
                "                          AND val.crnt_rec_flg = 1\n" +
                "                          AND val.del_flg = 0\n" +
                "                          AND val.ref_cd = '0005'\n" +
                "                   --JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id =:user\n" +
                "                   JOIN mw_pymt_sched_hdr psh\n" +
                "                       ON     app.loan_app_seq = psh.loan_app_seq\n" +
                "                          AND psh.crnt_rec_flg = 1\n" +
                "                   JOIN mw_pymt_sched_dtl psd\n" +
                "                       ON     psh.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq\n" +
                "                          AND psd.crnt_rec_flg = 1\n" +
                "                   JOIN mw_prd p\n" +
                "                       ON p.prd_seq = app.prd_seq AND p.crnt_rec_flg = 1\n" +
                "                   JOIN mw_port port\n" +
                "                       ON port.port_seq = clnt.port_key AND port.crnt_rec_flg = 1\n" +
                "                   JOIN mw_brnch brnch\n" +
                "                       ON     brnch.brnch_seq = port.brnch_seq\n" +
                "                          AND brnch.crnt_rec_flg = 1\n" +
                "                   LEFT OUTER JOIN\n" +
                "                   (  SELECT psd.pymt_sched_hdr_seq, SUM (rd.PYMT_AMT) rcvry_amt\n" +
                "                        FROM mw_rcvry_dtl rd\n" +
                "                             JOIN mw_pymt_sched_dtl psd\n" +
                "                                 ON     rd.PYMT_SCHED_DTL_SEQ =\n" +
                "                                        psd.PYMT_SCHED_DTL_SEQ\n" +
                "                                    AND psd.crnt_rec_flg = 1\n" +
                "                       WHERE     rd.CHRG_TYP_KEY IN\n" +
                "                                     (-1,\n" +
                "                                      (SELECT t.TYP_SEQ\n" +
                "                                         FROM mw_typs t\n" +
                "                                        WHERE     t.TYP_SEQ = rd.CHRG_TYP_KEY\n" +
                "                                              AND t.TYP_ID = '0017'\n" +
                "                                              AND t.CRNT_REC_FLG = 1))\n" +
                "                             AND rd.crnt_rec_flg = 1\n" +
                "                    GROUP BY pymt_sched_hdr_seq) rcvry\n" +
                "                       ON rcvry.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq\n" +
                "                   LEFT OUTER JOIN mw_dth_rpt dr\n" +
                "                       ON     dr.clnt_seq = clnt.clnt_seq\n" +
                "                          AND dr.crnt_rec_flg = 1\n" +
                "                          AND dr.crtd_dt > app.crtd_dt\n" +
                "                   LEFT OUTER JOIN mw_dsblty_rpt mdr\n" +
                "                       ON     mdr.clnt_seq = clnt.clnt_seq\n" +
                "                          AND mdr.crnt_rec_flg = 1\n" +
                "                          AND mdr.crtd_dt > app.crtd_dt\n" +
                "                   LEFT OUTER JOIN MW_ANML_RGSTR anm\n" +
                "                       ON     anm.LOAN_APP_SEQ = app.LOAN_APP_SEQ\n" +
                "                          AND anm.CRNT_REC_FLG = 1\n" +
                "                          AND anm.ANML_STS in (3,4)\n" +
                "                          AND anm.LAST_UPD_DT >= TRUNC (hdr.DSBMT_DT)\n" +
                "                   LEFT OUTER JOIN mw_exp e\n" +
                "                       ON     e.exp_ref = clnt.clnt_seq\n" +
                "                          AND e.crnt_rec_flg = 1\n" +
                "                          AND e.DEL_FLG = 0\n" +
                "                          AND e.CRTD_DT > app.CRTD_DT\n" +
                "                          AND e.EXPNS_TYP_SEQ IN\n" +
                "                                  (SELECT TYP_SEQ\n" +
                "                                     FROM mw_typs\n" +
                "                                    WHERE     CRNT_REC_FLG = 1\n" +
                "                                          AND typ_id IN ('0424', '0423'))\n" +
                "                   LEFT OUTER JOIN mw_clnt_rel rel\n" +
                "                       ON     rel.loan_app_seq = app.prnt_loan_app_seq\n" +
                "                          AND rel.crnt_rec_flg = 1\n" +
                "                          AND rel.rel_typ_flg = 1\n" +
                "                   LEFT OUTER JOIN mw_rcvry_trx mrt\n" +
                "                       ON mrt.rcvry_trx_seq =\n" +
                "                          (     SELECT rcvry_trx_seq\n" +
                "                                  FROM MW_RCVRY_TRX mrt\n" +
                "                                 WHERE     PYMT_REF = clnt.clnt_seq\n" +
                "                                       AND RCVRY_TYP_SEQ IN (454, 301)\n" +
                "                              ORDER BY EFF_START_DT DESC\n" +
                "                           FETCH FIRST 1 ROWS ONLY)\n" +
                "             WHERE clnt.crnt_rec_flg = 1 AND brnch.brnch_seq = :brnchSeq ?\n" +
                "          GROUP BY clnt.clnt_id,\n" +
                "                   clnt.clnt_seq,\n" +
                "                   clnt.frst_nm,\n" +
                "                   clnt.last_nm,\n" +
                "                   dr.dth_rpt_seq,\n" +
                "                   mdr.dsblty_rpt_seq,\n" +
                "                   anm.ANML_RGSTR_SEQ,\n" +
                "                   brnch.brnch_seq,\n" +
                "                   e.exp_seq,\n" +
                "                   prd_cmnt,\n" +
                "                   TRUNC (hdr.DSBMT_DT),\n" +
                "                   rel.rel_typ_flg,\n" +
                "                   e.post_flg,\n" +
                "                   p.prd_id,\n" +
                "                   app.prnt_loan_app_seq,\n" +
                "                   mrt.crnt_rec_flg)\n" +
                "GROUP BY clnt_id,\n" +
                "         clnt_seq,\n" +
                "         frst_nm,\n" +
                "         last_nm,\n" +
                "         dth_rpt_seq,\n" +
                "         dsblty_rpt_seq,\n" +
                "         ANML_RGSTR_SEQ,\n" +
                "         brnch_seq,\n" +
                "         post_flg,\n" +
                "         crnt_rec_flg";

        String countActiveClntScript = " SELECT count(distinct clnt.clnt_seq)  FROM mw_clnt clnt  \n" +
                "                JOIN mw_loan_app app ON app.clnt_seq = clnt.clnt_seq AND app.crnt_rec_flg = 1 --and app.prd_seq <> 29\n" +
                "                join MW_DSBMT_VCHR_HDR hdr on hdr.LOAN_APP_SEQ=app.LOAN_APP_SEQ and hdr.CRNT_REC_FLG=1 and hdr.DSBMT_VCHR_TYP=0\n" +
                "                JOIN mw_ref_cd_val val ON val.ref_cd_seq = app.loan_app_sts AND val.crnt_rec_flg = 1 AND val.del_flg = 0 AND val.ref_cd = '0005'  \n" +
                "                --JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id =:user \n" +
                "                JOIN mw_pymt_sched_hdr psh ON app.loan_app_seq = psh.loan_app_seq AND psh.crnt_rec_flg = 1 \n" +
                "                JOIN mw_pymt_sched_dtl psd ON psh.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq AND psd.crnt_rec_flg = 1  \n" +
                "                JOIN mw_prd p ON p.prd_seq = app.prd_seq AND p.crnt_rec_flg = 1  \n" +
                "                JOIN mw_port port ON port.port_seq = clnt.port_key AND port.crnt_rec_flg = 1  \n" +
                "                JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1  LEFT OUTER JOIN  ( \n" +
                "                SELECT psd.pymt_sched_hdr_seq,SUM(rd.PYMT_AMT) rcvry_amt  FROM mw_rcvry_dtl rd  \n" +
                "                JOIN mw_pymt_sched_dtl psd  ON rd.PYMT_SCHED_DTL_SEQ=psd.PYMT_SCHED_DTL_SEQ  AND psd.crnt_rec_flg = 1  \n" +
                "                where rd.CHRG_TYP_KEY in (-1,(select t.TYP_SEQ from mw_typs t where t.TYP_SEQ=rd.CHRG_TYP_KEY and t.TYP_ID='0017' and t.CRNT_REC_FLG = 1))  \n" +
                "                and rd.crnt_rec_flg = 1  group by pymt_sched_hdr_seq  \n" +
                "                ) rcvry ON rcvry.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq  LEFT OUTER JOIN mw_dth_rpt dr  \n" +
                "                ON dr.clnt_seq = clnt.clnt_seq  AND dr.crnt_rec_flg = 1  AND dr.crtd_dt > app.crtd_dt  \n" +
                "                LEFT OUTER JOIN mw_dsblty_rpt mdr  \n" +
                "                ON mdr.clnt_seq = clnt.clnt_seq  AND mdr.crnt_rec_flg = 1  AND mdr.crtd_dt > app.crtd_dt  \n" +
                "                LEFT OUTER JOIN mw_exp e  ON e.exp_ref = clnt.clnt_seq  AND e.crnt_rec_flg = 1 and e.DEL_FLG = 0 AND e.CRTD_DT > app.CRTD_DT  and e.EXPNS_TYP_SEQ IN (select TYP_SEQ from mw_typs where CRNT_REC_FLG = 1 and typ_id IN ('0424', '0423')) \n" +
                "                LEFT OUTER JOIN mw_clnt_rel rel ON rel.loan_app_seq = app.prnt_loan_app_seq  AND rel.crnt_rec_flg = 1  AND rel.rel_typ_flg = 1 \n" +
                "                WHERE clnt.crnt_rec_flg = 1 and brnch.brnch_seq=:brnchSeq ";

        if (filter != null && filter.length() > 0) {

            String search = " and (lower(clnt.clnt_id) like '%?%') ".replace("?", filter.toLowerCase());

            activeClntScript = activeClntScript.replace("?", search);
            countActiveClntScript += search;
        } else {
            activeClntScript = activeClntScript.replace("?", " ");
        }

        Query activeClntQuery = em.createNativeQuery(activeClntScript + "\r\norder by 1 desc")
                .setParameter("brnchSeq", brnchSeq).setHint(QueryHints.CACHEABLE, true);
        System.out.println(activeClntQuery);
        List<Object[]> clnts = activeClntQuery.setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        System.out.println(clnts.size());

        List<LoanServingDTO> dtoList = new ArrayList();
        clnts.forEach(c -> {
            LoanServingDTO dto = new LoanServingDTO();
            dto.clntId = c[0].toString();
            dto.clntSeq = c[1].toString();
            dto.frstNm = c[2] == null ? "" : c[2].toString();
            dto.lastNm = c[3] == null ? "" : c[3].toString();
            dto.loanAmt = c[4] == null ? 0 : new BigDecimal(c[4].toString()).longValue();
            dto.sercvChrgs = c[5] == null ? 0 : new BigDecimal(c[5].toString()).longValue();
            dto.rcvdAmt = c[6] == null ? 0 : new BigDecimal(c[6].toString()).longValue();
            dto.dthRptSeq = c[7] == null ? 0 : new BigDecimal(c[7].toString()).longValue();
            dto.amt = c[8] == null ? 0 : new BigDecimal(c[8].toString()).longValue();
            //Added by Areeba
            dto.dsbltyRptSeq = c[9] == null ? 0 : new BigDecimal(c[9].toString()).longValue();
            dto.dsbltyAmt = c[10] == null ? 0 : new BigDecimal(c[10].toString()).longValue();
            //Ended by Areeba
            dto.brnchSeq = c[11] == null ? 0 : new BigDecimal(c[11].toString()).longValue();
            dto.paid = c[12] == null ? false : true;
            dto.expSeq = c[12] == null ? "" : c[12].toString();
            // if ( c[ 10 ] != null ) {
            // List< Long > list = new ArrayList< Long >();
            // for ( String s : c[ 10 ].toString().split( "," ) )
            // list.add( Long.parseLong( s ) );
            // }
            // dto.expList = list;
            dto.prd = c[13] == null ? "" : c[13].toString();
            dto.disDate = c[14] == null ? "" : (c[14].toString().split(",", 1))[0]; //loan app sts date
            dto.relTypFlg = c[15] == null ? 0 : new BigDecimal(c[15].toString()).longValue();
            dto.post = c[16] == null ? false : true;
            dto.prdId = c[17] == null ? "" : c[17].toString();
            // updated by yousaf Dated: 04/07/2022 due to credit servicing double record shown
            dto.prntLoanAppSeq = c[18] == null ? "" : c[18].toString(); //c[ 16 ] == null ? 0 : new BigDecimal( c[ 16 ].toString() ).longValue();
            dto.rcvryCrntRecFlg = c[19] == null ? 0 : new BigDecimal(c[19].toString()).longValue();
            //Added by Yousaf Dated: 26-09-2022
            dto.anmlDthSeq = c[20] == null ? 0 : new BigDecimal(c[20].toString()).longValue();
            dto.anmlDthAmt = c[21] == null ? 0 : new BigDecimal(c[21].toString()).longValue();
            //Ended by Yousaf
            dtoList.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("clnts", dtoList);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(em.createNativeQuery(countActiveClntScript)
                    .setParameter("brnchSeq", brnchSeq).getSingleResult().toString()).longValue();
        }
        resp.put("count", totalCountResult);
        return resp;
    }

    //Added by Areeba - 22-04-2022 - OD CHECK
    public Map<String, Object> getAllClntsLoanApp(String userId, Long brnchSeq, Integer pageIndex, Integer pageSize, String filter,
                                                  Boolean isCount) {
        String activeClntScript = " SELECT clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq, max(od_chk_flg), max(clnt_od_days) from (\n" +
                " SELECT clnt.clnt_id, clnt.clnt_seq, clnt.frst_nm, clnt.last_nm, app.brnch_seq, app.loan_app_seq, app.od_chk_flg,\n" +
                "                       get_od_info (app.loan_app_seq, SYSDATE, 'd')     AS clnt_od_days\n" +
                "                  FROM mw_loan_app  app\n" +
                "                       JOIN mw_clnt clnt\n" +
                "                           ON clnt.clnt_seq = app.clnt_seq AND clnt.crnt_rec_flg = 1\n" +
                "                 WHERE  app.LOAN_APP_STS = 703 and app.CRNT_REC_FLG = 1 and  app.brnch_seq = :brnchSeq ? \n" +
                "                       AND get_od_info (app.loan_app_seq, SYSDATE, 'd') > 1 \n" +
                "                 ORDER BY app.EFF_START_DT DESC)\n" +
                "                 group by clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq ";

        String countActiveClntScript = " SELECT COUNT(*) FROM\n" +
                " (SELECT clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq, max(od_chk_flg), max(clnt_od_days) from (\n" +
                " SELECT clnt.clnt_id, clnt.clnt_seq, clnt.frst_nm, clnt.last_nm, app.brnch_seq, app.loan_app_seq, app.od_chk_flg,\n" +
                "                       get_od_info (app.loan_app_seq, SYSDATE, 'd')     AS clnt_od_days\n" +
                "                  FROM mw_loan_app  app\n" +
                "                       JOIN mw_clnt clnt\n" +
                "                           ON clnt.clnt_seq = app.clnt_seq AND clnt.crnt_rec_flg = 1\n" +
                "                 WHERE  app.LOAN_APP_STS = 703 and app.CRNT_REC_FLG = 1 and  app.brnch_seq = :brnchSeq ? \n" +
                "                       AND get_od_info (app.loan_app_seq, SYSDATE, 'd') > 1 \n" +
                "                 ORDER BY app.EFF_START_DT DESC)\n" +
                "                 group by clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq) ";
        if (filter != null && filter.length() > 0) {

            String search = (" and ((lower(clnt.clnt_id) like '%?%') or (lower(clnt.frst_nm) like '%?%')" +
                    " or (lower(clnt.last_nm) like '%?%')) ").replace("?", filter.toLowerCase());

            activeClntScript = activeClntScript.replace("?", search);
            countActiveClntScript = countActiveClntScript.replace("?", search);
        } else {
            activeClntScript = activeClntScript.replace("?", " ");
            countActiveClntScript = countActiveClntScript.replace("?", " ");
        }

        Query activeClntQuery = em.createNativeQuery(activeClntScript).setParameter("brnchSeq", brnchSeq).setHint(QueryHints.CACHEABLE, true);

        List<Object[]> clnts = activeClntQuery.setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<BypassOdDto> dtoList = new ArrayList();
        clnts.forEach(c -> {
            BypassOdDto dto = new BypassOdDto();
            dto.clntId = c[0] == null ? 0 : new BigDecimal(c[0].toString()).longValue();
            dto.clntSeq = c[1] == null ? 0 : new BigDecimal(c[1].toString()).longValue();
            dto.frstNm = c[2] == null ? "" : c[2].toString();
            dto.lastNm = c[3] == null ? "" : c[3].toString();
            dto.brnchSeq = c[4] == null ? 0 : new BigDecimal(c[4].toString()).longValue();
            dto.odChkFlg = c[5] == null ? 0 : new BigDecimal(c[5].toString()).longValue();
            dto.clntOdDays = c[6] == null ? 0 : new BigDecimal(c[6].toString()).longValue();
            dtoList.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("clnts", dtoList);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(em.createNativeQuery(countActiveClntScript).setParameter("brnchSeq", brnchSeq).getSingleResult().toString()).longValue();
        }
        resp.put("count", totalCountResult);
        return resp;
    }

    public Map<String, Object> getBypassODClntsLoanApp(Long brnchSeq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        String activeClntScript = " SELECT clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq, max(od_chk_flg), max(clnt_od_days) from (            \n" +
                " SELECT clnt.clnt_id, clnt.clnt_seq, clnt.frst_nm, clnt.last_nm, app.brnch_seq, app.loan_app_seq, app.od_chk_flg,\n" +
                "                       get_od_info (app.loan_app_seq, SYSDATE, 'd')     AS clnt_od_days\n" +
                "                  FROM mw_loan_app  app\n" +
                "                       JOIN mw_clnt clnt\n" +
                "                           ON clnt.clnt_seq = app.clnt_seq AND clnt.crnt_rec_flg = 1\n" +
                "                 WHERE  app.LOAN_APP_STS = 703 and app.CRNT_REC_FLG = 1 and    app.brnch_seq = :brnchSeq and app.od_chk_flg = 1\n" +
                "                       AND get_od_info (app.loan_app_seq, SYSDATE, 'd') > 1\n" +
                "                 ORDER BY app.EFF_START_DT DESC)\n" +
                "                 group by clnt_id, clnt_seq, frst_nm, last_nm, brnch_seq ";

        Query activeClntQuery = em.createNativeQuery(activeClntScript).setParameter("brnchSeq", brnchSeq).setHint(QueryHints.CACHEABLE, true);

        List<Object[]> clnts = activeClntQuery.getResultList();

        List<BypassOdDto> dtoList = new ArrayList();
        clnts.forEach(c -> {
            BypassOdDto dto = new BypassOdDto();
            dto.clntId = c[0] == null ? 0 : new BigDecimal(c[0].toString()).longValue();
            dto.clntSeq = c[1] == null ? 0 : new BigDecimal(c[1].toString()).longValue();
            dto.frstNm = c[2] == null ? "" : c[2].toString();
            dto.lastNm = c[3] == null ? "" : c[3].toString();
            dto.brnchSeq = c[4] == null ? 0 : new BigDecimal(c[4].toString()).longValue();
            dto.odChkFlg = c[5] == null ? 0 : new BigDecimal(c[5].toString()).longValue();
            dto.clntOdDays = c[6] == null ? 0 : new BigDecimal(c[6].toString()).longValue();
            dtoList.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("clnts", dtoList);

        return resp;
    }

    public int updateOD(long clntSeq, Boolean checked) {

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        List<MwLoanApp> loan = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlg(clntSeq, true);
        for (MwLoanApp app : loan) {
            if (app != null) {
                app.setLastUpdBy(currUser);
                app.setLastUpdDt(Instant.now());
                app.setOdChkFlg(checked);

                mwLoanAppRepository.save(app);
            } else {
                return -1;
            }
        }
        return 1;

    }
    //Ended by Areeba

    private String getFormaedDate(String input) {
        String date = "";
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").format(inputFormat.parse(input));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

}
