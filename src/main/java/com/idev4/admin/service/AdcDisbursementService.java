package com.idev4.admin.service;

import com.idev4.admin.dto.AdcDisbursementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Added by Naveed - Date - 10-05-2022
 * SCR - MCB Disbursement
 */

@Service
public class AdcDisbursementService {

    private final Logger log = LoggerFactory.getLogger(ClientTagListService.class);

    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Map<String, Object> getAllByBranch(long brnchSeq, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        log.info("ADC_Disbursement --> BRANCH_SEQ = " + brnchSeq + ", pageIndex = " + pageIndex + ", pageSize = " + pageSize + ", filter = " + filter);

        String script = "SELECT CLNT.CLNT_ID,\n" +
                "       CLNT.FRST_NM || ' ' || CLNT.LAST_NM\n" +
                "           CLNT_NM,\n" +
                "       LA.LOAN_APP_SEQ,\n" +
                "       (SELECT VAL.REF_CD_DSCR\n" +
                "          FROM MW_REF_CD_VAL VAL\n" +
                "         WHERE VAL.REF_CD_SEQ = ADQ.DSBMT_STS_SEQ AND VAL.CRNT_REC_FLG = 1)\n" +
                "           DSBMT_STS,\n" +
                "       TO_CHAR (ADQ.DSBMT_STS_DT, 'dd-MON-yyyy')\n" +
                "           DSBMT_DATE,\n" +
                "       (SELECT VAL.REF_CD_DSCR\n" +
                "          FROM MW_REF_CD_VAL VAL\n" +
                "         WHERE VAL.REF_CD_SEQ = ADQ.ADC_STS_SEQ AND VAL.CRNT_REC_FLG = 1)\n" +
                "           ADC_STS,\n" +
                "       TO_CHAR (ADQ.ADC_STS_DT, 'dd-MON-yyyy')\n" +
                "           ADC_DATE, \n" +
                "       ADQ.DSBMT_DTL_KEY, ADQ.DSBMT_HDR_SEQ, DVD.AMT, ADQ.REMARKS\n" +
                "  FROM MW_ADC_DSBMT_QUE  ADQ\n" +
                "       JOIN MW_DSBMT_VCHR_DTL DVD\n" +
                "           ON DVD.DSBMT_DTL_KEY = ADQ.DSBMT_DTL_KEY\n" +
                "       JOIN MW_DSBMT_VCHR_HDR DVH\n" +
                "           ON DVH.DSBMT_HDR_SEQ = ADQ.DSBMT_HDR_SEQ\n" +
                "       JOIN MW_LOAN_APP LA\n" +
                "           ON     LA.LOAN_APP_SEQ = DVH.LOAN_APP_SEQ\n" +
                "              AND (   (LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_STS IN (703, 1305))\n" +
                "                   OR (LA.CRNT_REC_FLG = 0 AND LA.LOAN_APP_STS IN (1285)))\n" +
                "              AND LA.BRNCH_SEQ =\n" +
                "                  CASE\n" +
                "                      WHEN :BRNCH_SEQ > 0 THEN :BRNCH_SEQ\n" +
                "                      ELSE LA.BRNCH_SEQ\n" +
                "                  END" +
                "       JOIN MW_CLNT CLNT\n" +
                "           ON CLNT.CLNT_SEQ = LA.CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1\n" +
                " WHERE ADQ.CRNT_REC_FLG = 1";

        String scriptCount = "SELECT COUNT(1)\n" +
                "  FROM MW_ADC_DSBMT_QUE  ADQ\n" +
                "       JOIN MW_DSBMT_VCHR_DTL DVD\n" +
                "           ON DVD.DSBMT_DTL_KEY = ADQ.DSBMT_DTL_KEY AND DVD.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_DSBMT_VCHR_HDR DVH\n" +
                "           ON DVH.DSBMT_HDR_SEQ = ADQ.DSBMT_HDR_SEQ AND DVH.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_LOAN_APP LA\n" +
                "           ON     LA.LOAN_APP_SEQ = DVH.LOAN_APP_SEQ\n" +
                "              AND (   (LA.CRNT_REC_FLG = 1 AND LA.LOAN_APP_STS IN (703, 1305))\n" +
                "                   OR (LA.CRNT_REC_FLG = 0 AND LA.LOAN_APP_STS IN (1285)))\n" +
                "              AND  LA.BRNCH_SEQ =\n" +
                "                  CASE\n" +
                "                      WHEN :BRNCH_SEQ > 0 THEN :BRNCH_SEQ\n" +
                "                      ELSE LA.BRNCH_SEQ\n" +
                "                  END" +
                "       JOIN MW_CLNT CLNT\n" +
                "           ON CLNT.CLNT_SEQ = LA.CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1\n" +
                " WHERE ADQ.CRNT_REC_FLG = 1";

        if (filter != null && filter.length() > 0) {

            String search = (" and ((CLNT.CLNT_ID like '%?%') OR (lower(CLNT.FRST_NM) like lower('%?%')) OR (lower(CLNT.LAST_NM) like lower('%?%')) " +
                    " OR (LA.LOAN_APP_SEQ like '%?%')) ").replace("?", filter.toLowerCase());

            script += search;
            scriptCount += search;
        }

        List<Object[]> records = entityManager.createNativeQuery(script + "\r\norder by ADQ.DSBMT_STS_DT desc").setParameter("BRNCH_SEQ", brnchSeq)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<AdcDisbursementDto> dtoList = new ArrayList<AdcDisbursementDto>();

        records.forEach(r -> {
            AdcDisbursementDto dto = new AdcDisbursementDto();
            dto.clientId = r[0] == null ? "" : r[0].toString();
            dto.clientName = r[1] == null ? "" : r[1].toString();
            dto.loanAppId = r[2] == null ? "" : r[2].toString();
            dto.disburseSts = r[3] == null ? "" : r[3].toString();
            dto.disburseDate = r[4] == null ? "" : r[4].toString();
            dto.adcSts = r[5] == null ? "" : r[5].toString();
            dto.adcDate = r[6] == null ? "" : r[6].toString();

            dto.dsbmtDtlKey = r[7] == null ? "" : r[7].toString();
            dto.dsbmtHdrSeq = r[8] == null ? "" : r[8].toString();
            dto.dsbmtAmt = r[9] == null ? "" : r[9].toString();
            dto.remarks = r[10] == null ? "" : r[10].toString();

            dtoList.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("clients", dtoList);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(entityManager.createNativeQuery(scriptCount)
                    .setParameter("BRNCH_SEQ", brnchSeq)
                    .getSingleResult().toString()).longValue();
        }

        resp.put("count", totalCountResult);
        return resp;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> updDsbmtRvrslReason(Long dsbmtDtlKey, String remarks) {
        Map<String, Object> resp = new HashMap<>();
        log.info("Reversal Reason: " + dsbmtDtlKey);

        Query query = entityManager.createNativeQuery("UPDATE MW_ADC_DSBMT_QUE SET REMARKS = '" + remarks + "'," +
                        " REVRSL_REQ_DT = SYSDATE" +
                        " WHERE DSBMT_DTL_KEY = :dsbmtDtlKey AND CRNT_REC_FLG = 1")
                .setParameter("dsbmtDtlKey", dsbmtDtlKey);
        Object updResObj = query.executeUpdate();
        resp.put("updated", updResObj);
        return resp;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> discardDsbmtRvrslReason(Long dsbmtDtlKey) {
        Map<String, Object> resp = new HashMap<>();
        log.info("Discard Reversal Reason: " + dsbmtDtlKey);

        Query query = entityManager.createNativeQuery("UPDATE MW_ADC_DSBMT_QUE SET REMARKS = NULL, " +
                        " REVRSL_REQ_DT = NULL" +
                        " WHERE DSBMT_DTL_KEY = :dsbmtDtlKey AND CRNT_REC_FLG = 1")
                .setParameter("dsbmtDtlKey", dsbmtDtlKey);
        Object updResObj = query.executeUpdate();
        resp.put("reversed", updResObj);
        return resp;
    }
}
