package com.idev4.admin.service;

import com.idev4.admin.domain.MwFundsRequest;
import com.idev4.admin.dto.MwFundsRequestDto;
import com.idev4.admin.repository.MwFundsRequestRepository;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Added, Naveed
 * @Date, 14-06-2022
 * @Description, SCR - systemization Funds Request
 */

@Service
public class MwFundsRequestService {

    private final Logger log = LoggerFactory.getLogger(MwFundsRequestService.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MwFundsRequestRepository requestRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getAllBranchFunds(String toDate, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        log.info("Branch Funds Request --> pageIndex = " + pageIndex + ", pageSize = " + pageSize + ", filter = " + filter);

        String script = "  SELECT REG.REG_SEQ,\n" +
                "         REG.REG_NM,\n" +
                "         NULL\n" +
                "             AREA_SEQ,\n" +
                "         NULL\n" +
                "             AREA_NM,\n" +
                "         NULL\n" +
                "             BRNCH_SEQ,\n" +
                "         NULL\n" +
                "             BRNCH_NM,\n" +
                "         AC.ACCT_NUM,\n" +
                "         SUM (MFR.AMT),\n" +
                "         LISTAGG (MFR.REMARKS, ', ') WITHIN GROUP (ORDER BY MFR.REMARKS)\n" +
                "             \"REMARKS\"\n" +
                "    FROM MW_REG REG\n" +
                "         JOIN MW_REG_ACCT_SET AC\n" +
                "             ON AC.REG_SEQ = REG.REG_SEQ AND AC.CRNT_REC_FLG = 1\n" +
                "         LEFT OUTER JOIN MW_FUND_REQUEST MFR\n" +
                "             ON     MFR.ENTY_SEQ = REG.REG_SEQ\n" +
                "                AND MFR.ENTY_TYP = 'REG'\n" +
                "                AND TRUNC (MFR.CRTD_DT) =\n" +
                "                    CASE\n" +
                "                        WHEN :P_TO_DATE <> '-1'\n" +
                "                        THEN\n" +
                "                            TO_DATE ( :P_TO_DATE, 'dd-MM-yyyy')\n" +
                "                        ELSE\n" +
                "                            TRUNC (SYSDATE)\n" +
                "                    END\n" +
                "                AND MFR.CRNT_REC_FLG = 1 \n" +
                "   WHERE REG.CRNT_REC_FLG = 1 %%%\n" +
                "GROUP BY REG.REG_SEQ,\n" +
                "         REG.REG_NM,\n" +
                "         REG.REG_NM,\n" +
                "         NULL,\n" +
                "         NULL,\n" +
                "         NULL,\n" +
                "         NULL,\n" +
                "         AC.ACCT_NUM\n" +
                "UNION ALL\n" +
                "  SELECT REG.REG_SEQ,\n" +
                "         REG.REG_NM,\n" +
                "         AR.AREA_SEQ,\n" +
                "         AR.AREA_NM,\n" +
                "         BR.BRNCH_SEQ,\n" +
                "         BR.BRNCH_NM,\n" +
                "         AC.ACCT_NUM,\n" +
                "         SUM (MFR.AMT),\n" +
                "         LISTAGG (MFR.REMARKS, ', ') WITHIN GROUP (ORDER BY MFR.REMARKS)\n" +
                "             \"REMARKS\"\n" +
                "    FROM MW_REG REG\n" +
                "         JOIN MW_AREA AR ON AR.REG_SEQ = REG.REG_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_BRNCH BR ON BR.AREA_SEQ = AR.AREA_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_BRNCH_ACCT_SET AC\n" +
                "             ON AC.BRNCH_SEQ = BR.BRNCH_SEQ AND AC.CRNT_REC_FLG = 1\n" +
                "         LEFT OUTER JOIN MW_FUND_REQUEST MFR\n" +
                "             ON     MFR.ENTY_SEQ = BR.BRNCH_SEQ\n" +
                "                AND MFR.ENTY_TYP = 'BR'\n" +
                "                AND TRUNC (MFR.CRTD_DT) =\n" +
                "                    CASE\n" +
                "                        WHEN :P_TO_DATE <> '-1'\n" +
                "                        THEN\n" +
                "                            TO_DATE ( :P_TO_DATE, 'dd-MM-yyyy')\n" +
                "                        ELSE\n" +
                "                            TRUNC (SYSDATE)\n" +
                "                    END\n" +
                "                AND MFR.CRNT_REC_FLG = 1\n" +
                "   WHERE REG.CRNT_REC_FLG = 1   %%\n" +
                "GROUP BY REG.REG_SEQ,\n" +
                "         REG.REG_NM,\n" +
                "         AR.AREA_SEQ,\n" +
                "         AR.AREA_NM,\n" +
                "         BR.BRNCH_SEQ,\n" +
                "         BR.BRNCH_NM,\n" +
                "         AC.ACCT_NUM ";

        String scriptCount = "SELECT COUNT(REG.REG_SEQ)  FROM MW_REG  REG\n" +
                "       JOIN MW_AREA AR ON AR.REG_SEQ = REG.REG_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_BRNCH BR ON BR.AREA_SEQ = AR.AREA_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_BRNCH_ACCT_SET AC\n" +
                "           ON AC.BRNCH_SEQ = BR.BRNCH_SEQ AND AC.CRNT_REC_FLG = 1\n" +
                " WHERE REG.CRNT_REC_FLG = 1 %% \n";

        String regSearch = "";
        String BrnchSearch = "";
        if (filter != null && filter.length() > 0) {

            BrnchSearch = (" and ( (lower(REG.REG_NM) like lower('%?%')) OR (lower(BR.BRNCH_NM) like lower('%?%'))) ")
                    .replace("?", filter.toLowerCase());

            regSearch = (" and (lower(REG.REG_NM) like lower('%?%')) ")
                    .replace("?", filter.toLowerCase());
        }
        script = script.replaceAll("%%%", regSearch).replaceAll("%%", BrnchSearch);
        scriptCount = scriptCount.replaceAll("%%", BrnchSearch);

        List<Object[]> records = entityManager.createNativeQuery(script)
                .setParameter("P_TO_DATE", toDate).setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<MwFundsRequestDto> lists = new ArrayList();

        records.forEach(r -> {
            MwFundsRequestDto dto = new MwFundsRequestDto();

            dto.regSeq = r[0] == null ? "0" : r[0].toString();
            dto.regNm = r[1] == null ? "" : r[1].toString();
            dto.areaSeq = r[2] == null ? "0" : r[2].toString();
            dto.areaNm = r[3] == null ? "" : r[3].toString();
            dto.brnchSeq = r[4] == null ? "0" : r[4].toString();
            dto.brnchNm = r[5] == null ? "" : r[5].toString();
            dto.acctNm = r[6] == null ? "" : r[6].toString();
            dto.expAmt = r[7] == null ? "" : r[7].toString();
            dto.expDscr = r[8] == null ? "" : r[8].toString();

            lists.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("lists", lists);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(entityManager.createNativeQuery(scriptCount)
                    .getSingleResult().toString()).longValue();
        }

        resp.put("count", totalCountResult);
        return resp;
    }

    public Map<String, Object> addBranchFudsRequest(MwFundsRequestDto dto, String user) {
        Map<String, Object> resp = new HashMap<>();
        Instant instant = Instant.now();

        long seq = SequenceFinder.findNextVal(Sequences.FUND_REQ_SEQ);
        MwFundsRequest request = new MwFundsRequest();

        if (dto.entyTyp.equals("BR")) {
            request.setEntySeq(Long.parseLong(dto.brnchSeq));
        } else {
            request.setEntySeq(Long.parseLong(dto.regSeq));
        }

        request.setFundReqSeq(seq);
        request.setEntyTyp(dto.entyTyp);
        request.setAmt(Long.parseLong(dto.expAmt));
        request.setRemarks(dto.expDscr);
        request.setCrtdDt(instant);
        request.setCrntRecFlg(true);
        request.setCrdtBy(user);

        MwFundsRequest saveRequest = requestRepository.save(request);

        if (saveRequest != null) {
            resp.put("success", "Submit Branch Funds Request Successfully");
        } else {
            resp.put("error", "Branch Funds Request was not Submit");
        }
        return resp;
    }

    public Map<String, Object> getFundDetailByAccountNumAndDate(String acctNum, String toDate) {
        Map<String, Object> resp = new HashMap<>();

        String script = "SELECT REG.REG_SEQ,\n" +
                "       REG.REG_NM,\n" +
                "       NULL     AREA_SEQ,\n" +
                "       NULL     AREA_NM,\n" +
                "       NULL     BRNCH_SEQ,\n" +
                "       NULL     BRNCH_NM,\n" +
                "       AC.ACCT_NUM,\n" +
                "       MFR.AMT,\n" +
                "       MFR.REMARKS,\n" +
                "       MFR.FUND_REQ_SEQ\n" +
                "  FROM MW_REG_ACCT_SET  AC\n" +
                "       JOIN MW_FUND_REQUEST MFR\n" +
                "           ON     TRUNC (MFR.CRTD_DT) =\n" +
                "                  CASE\n" +
                "                      WHEN :P_TO_DATE <> '-1'\n" +
                "                      THEN\n" +
                "                          TO_DATE ( :P_TO_DATE, 'dd-MM-yyyy')\n" +
                "                      ELSE\n" +
                "                          TRUNC (SYSDATE)\n" +
                "                  END\n" +
                "              AND MFR.ENTY_TYP = 'REG'\n" +
                "              AND MFR.ENTY_SEQ = AC.REG_SEQ\n" +
                "              AND MFR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_REG REG ON REG.REG_SEQ = AC.REG_SEQ AND REG.CRNT_REC_FLG = 1\n" +
                " WHERE AC.ACCT_NUM = :P_ACCT_NUM AND AC.CRNT_REC_FLG = 1\n" +
                "UNION ALL\n" +
                "SELECT REG.REG_SEQ,\n" +
                "       REG.REG_NM,\n" +
                "       AR.AREA_SEQ,\n" +
                "       AR.AREA_NM,\n" +
                "       BR.BRNCH_SEQ,\n" +
                "       BR.BRNCH_NM,\n" +
                "       AC.ACCT_NUM,\n" +
                "       MFR.AMT,\n" +
                "       MFR.REMARKS,\n" +
                "       MFR.FUND_REQ_SEQ\n" +
                "  FROM MW_BRNCH_ACCT_SET  AC\n" +
                "       JOIN MW_FUND_REQUEST MFR\n" +
                "           ON     TRUNC (MFR.CRTD_DT) =\n" +
                "                  CASE\n" +
                "                      WHEN :P_TO_DATE <> '-1'\n" +
                "                      THEN\n" +
                "                          TO_DATE ( :P_TO_DATE, 'dd-MM-yyyy')\n" +
                "                      ELSE\n" +
                "                          TRUNC (SYSDATE)\n" +
                "                  END\n" +
                "              AND MFR.ENTY_TYP = 'BR'\n" +
                "              AND MFR.ENTY_SEQ = AC.BRNCH_SEQ\n" +
                "              AND MFR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_BRNCH BR\n" +
                "           ON BR.BRNCH_SEQ = AC.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_AREA AR ON AR.AREA_SEQ = BR.AREA_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "       JOIN MW_REG REG ON REG.REG_SEQ = AR.REG_SEQ AND REG.CRNT_REC_FLG = 1\n" +
                " WHERE AC.ACCT_NUM = :P_ACCT_NUM AND AC.CRNT_REC_FLG = 1 ";

        List<Object[]> records = entityManager.createNativeQuery(script).setParameter("P_ACCT_NUM", acctNum).setParameter("P_TO_DATE", toDate).getResultList();

        List<MwFundsRequestDto> lists = new ArrayList();

        records.forEach(r -> {
            MwFundsRequestDto dto = new MwFundsRequestDto();

            dto.regSeq = r[0] == null ? "0" : r[0].toString();
            dto.regNm = r[1] == null ? "" : r[1].toString();
            dto.areaSeq = r[2] == null ? "0" : r[2].toString();
            dto.areaNm = r[3] == null ? "" : r[3].toString();
            dto.brnchSeq = r[4] == null ? "0" : r[4].toString();
            dto.brnchNm = r[5] == null ? "" : r[5].toString();
            dto.acctNm = r[6] == null ? "" : r[6].toString();
            dto.expAmt = r[7] == null ? "" : r[7].toString();
            dto.expDscr = r[8] == null ? "" : r[8].toString();
            dto.fundSeq = r[9] == null ? "0" : r[9].toString();

            lists.add(dto);
        });
        resp.put("lists", lists);

        return resp;
    }

    public Map<String, Object> updateBranchFudsRequest(MwFundsRequestDto fundsDto, String user) {
        Map<String, Object> resp = new HashMap<>();
        Instant instant = Instant.now();

        MwFundsRequest request = requestRepository.findByFundReqSeqAndCrntRecFlg(Long.parseLong(fundsDto.fundSeq), true);
        if (request == null) {
            resp.put("error", "fund request not found");
            return resp;
        }

        request.setAmt(Long.parseLong(fundsDto.expAmt));
        request.setRemarks(fundsDto.expDscr);
        request.setLastUpDt(instant);
        request.setLastUpdBy(user);

        MwFundsRequest saveRequest = requestRepository.save(request);

        if (saveRequest != null) {
            resp.put("success", "Submit Branch Funds Request Successfully");
        } else {
            resp.put("error", "Branch Funds Request was not Submit");
        }
        return resp;
    }
}
