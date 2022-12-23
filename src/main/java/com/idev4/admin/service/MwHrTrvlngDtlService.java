package com.idev4.admin.service;


/*Authored by Areeba
HR Travelling SCR
Dated - 23-06-2022
*/

import com.idev4.admin.domain.MwHrTrvlngDtl;
import com.idev4.admin.dto.MwHrTrvlngDtlDTO;
import com.idev4.admin.repository.MwHrTrvlngDtlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MwHrTrvlngDtlService {

    private final Logger log = LoggerFactory.getLogger(MwHrTrvlngDtlService.class);

    private final MwHrTrvlngDtlRepository mwHrTrvlngDtlRepository;

    private final EntityManager em;

    public MwHrTrvlngDtlService(MwHrTrvlngDtlRepository mwHrTrvlngDtlRepository, EntityManager em) {
        this.mwHrTrvlngDtlRepository = mwHrTrvlngDtlRepository;
        this.em = em;
    }

    public List<MwHrTrvlngDtl> getTrvlngDtl() {
        return mwHrTrvlngDtlRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllTrvlngDtls(String monthDt, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String trvlngScript = " SELECT REG_NM, AREA_NM, BRNCH_NM, PORT_NM, \n" +
                " HRID, DISB_CLNTS, DISB_AMT, TRVLNG_ROL, FIELD_TYP, INCNTVE FROM (\n" +
                " SELECT NVL ((SELECT REG_NM FROM MW_REG WHERE REG_SEQ = TRV.REG_SEQ), 'not found') AS REG_NM,\n" +
                " NVL ((SELECT AREA_NM FROM MW_AREA WHERE AREA_SEQ = TRV.AREA_SEQ), 'not found') AS AREA_NM,\n" +
                " NVL ((SELECT BRNCH_NM FROM MW_BRNCH WHERE BRNCH_SEQ = TRV.BRNCH_SEQ), 'not found') AS BRNCH_NM,\n" +
                " NVL ((SELECT PORT.PORT_NM FROM MW_PORT PORT WHERE PORT_SEQ = TRV.PORT_SEQ), 'not found') AS PORT_NM,\n" +
                " TRV.HRID, TRV.DISB_CLNTS, TRV.DISB_AMT, TRV.TRVLNG_ROL, TRV.FIELD_TYP, TRV.INCNTVE FROM (\n" +
                " SELECT HTD.REG_SEQ, HTD.AREA_SEQ, HTD.BRNCH_SEQ, HTD.PORT_SEQ,\n" +
                "    HTD.HRID, HTD.DISB_CLNTS, HTD.DISB_AMT, HTD.REF_CD_TRVLNG_ROL_DSCR AS TRVLNG_ROL,\n" +
                "    HTD.FIELD_TYPE_DSCR AS FIELD_TYP, SUM (HTD.INCTVE) AS INCNTVE\n" +
                "    FROM MW_HR_TRVLNG_DTL HTD \n" +
                "        WHERE HTD.TRVLNG_MNTH = TO_DATE ( :monthDt)  \n" +
                "        GROUP BY HTD.REG_SEQ, HTD.AREA_SEQ, HTD.BRNCH_SEQ, HTD.PORT_SEQ, HTD.DISB_CLNTS,\n" +
                "        HTD.DISB_AMT, HTD.REF_CD_TRVLNG_ROL_DSCR, HTD.FIELD_TYPE_DSCR, HTD.HRID \n" +
                "        ORDER BY HTD.REF_CD_TRVLNG_ROL_DSCR, HTD.PORT_SEQ, HTD.HRID) TRV) ? ";
        String trvlngCountScript = "SELECT COUNT(*) FROM ( " + trvlngScript + " ) ";
        String search = " ";
        if (filter != null && filter.length() > 0) {
            search = (" WHERE ((LOWER(REG_NM) LIKE '%?%' ) OR (LOWER(AREA_NM) LIKE '%?%') \n" +
                    "                                         OR (LOWER(BRNCH_NM) LIKE '%?%') \n" +
                    "                                         OR ( LOWER(PORT_NM) LIKE '%?%') OR (LOWER(TRVLNG_ROL) LIKE '%?%')\n" +
                    "                                         OR (LOWER(FIELD_TYP) LIKE '%?%') OR (HRID LIKE '%?%')) ")
                    .replace("?", filter.toLowerCase());
        }
        trvlngScript = trvlngScript.replace("?", search);
        trvlngCountScript = trvlngCountScript.replace("?", search);

        List<Object[]> allTrvlngDtlList = em.createNativeQuery(trvlngScript)
                .setParameter("monthDt", monthDt)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<MwHrTrvlngDtlDTO> dtoList = new ArrayList();
        allTrvlngDtlList.forEach(l -> {
            MwHrTrvlngDtlDTO dto = new MwHrTrvlngDtlDTO();
            dto.regNm = l[0] == null ? "" : l[0].toString();
            dto.areaNm = l[1] == null ? "" : l[1].toString();
            dto.brnchNm = l[2] == null ? "" : l[2].toString();
            dto.portNm = l[3] == null ? "" : l[3].toString();
            dto.hrid = l[4] == null ? "" : l[4].toString();
            dto.disbClnts = l[5] == null ? 0 : new BigDecimal(l[5].toString()).longValue();
            dto.disbAmt = l[6] == null ? 0 : new BigDecimal(l[6].toString()).longValue();
            dto.refCdTrvlngRolDscr = l[7] == null ? "" : l[7].toString();
            dto.fieldTypeDscr = l[8] == null ? "" : l[8].toString();
            dto.inctve = l[9] == null ? 0 : new BigDecimal(l[9].toString()).longValue();

            dtoList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TrvlngDtl", dtoList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    em.createNativeQuery(trvlngCountScript)
                            .setParameter("monthDt", monthDt)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    @Transactional
    public Map<String, Object> callTrvlngCalc(String monthDt) {

        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(monthDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Object> mapResp = new HashMap<>();

        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("PRC_CALC_HR_TRV");
        storedProcedure.registerStoredProcedureParameter("p_to_date", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_out_msg", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("p_to_date", date);
        storedProcedure.execute();

        String parmOutputProcedure = storedProcedure.getOutputParameterValue("p_out_msg").toString();

        if (parmOutputProcedure.contains("success")) {
            mapResp.put("Response", "SUCCESS");
        } else {
            mapResp.put("Response", parmOutputProcedure);
        }
        return mapResp;
    }

}
