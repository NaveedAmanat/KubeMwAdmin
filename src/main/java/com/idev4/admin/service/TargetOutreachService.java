package com.idev4.admin.service;

import com.idev4.admin.domain.MwBrnchTrgt;
import com.idev4.admin.domain.RegionWiseOutreach;
import com.idev4.admin.dto.MwBrnchTrgtDTO;
import com.idev4.admin.dto.RegionWiseOutreachDTO;
import com.idev4.admin.repository.MwBrnchTrgtRepository;
import com.idev4.admin.repository.RegionWiseOutreachRepository;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TargetOutreachService {

    private final Logger log = LoggerFactory.getLogger(TargetOutreachService.class);

    @Autowired
    MwBrnchTrgtRepository mwBrnchTrgtRepository;

    @Autowired
    RegionWiseOutreachRepository regionWiseOutreachRepository;

    @Autowired
    EntityManager em;

    @Transactional(readOnly = true)
    public Map<String, Object> getMwBrnchTrgt(String monthDt, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String brnchTrgtScript = " SELECT MBT.BRNCH_TARGETS_SEQ, MBT.TRGT_YR, MBT.TRGT_PERD, MBT.TRGT_CLIENTS, \n" +
                "                 MBT.TRGT_AMT, MBT.BRNCH_SEQ, BRNCH.BRNCH_NM, MBT.PRD_SEQ, PRD.PRD_GRP_NM  FROM MW_BRNCH_TRGT MBT \n" +
                "                 JOIN MW_BRNCH BRNCH ON BRNCH.BRNCH_SEQ = MBT.BRNCH_SEQ AND BRNCH.CRNT_REC_FLG = 1\n" +
                "                 JOIN MW_PRD_GRP PRD ON PRD.PRD_GRP_SEQ = MBT.PRD_SEQ AND PRD.CRNT_REC_FLG = 1\n" +
                "                 WHERE MBT.DEL_FLG = 0 AND MBT.TRGT_PERD = \n" +
                "                 TO_NUMBER (TO_CHAR(TO_DATE(:monthDt, 'dd-mm-rrrr'), 'yyyy') || TO_CHAR(TO_DATE(:monthDt, 'dd-mm-rrrr'), 'MM')) ? " +
                "                   ORDER BY MBT.BRNCH_TARGETS_SEQ DESC ";
        String brnchTrgtCountScript = "SELECT COUNT(*) FROM ( " + brnchTrgtScript + " ) ";
        String search = " ";
        if (filter != null && filter.length() > 0) {
            search = (" AND ((LOWER(BRNCH.BRNCH_NM) LIKE '%?%' ) " +
                    " OR (LOWER(PRD.PRD_GRP_NM) LIKE '%?%' )) ")
                    .replace("?", filter.toLowerCase());
        }
        brnchTrgtScript = brnchTrgtScript.replace("?", search);
        brnchTrgtCountScript = brnchTrgtCountScript.replace("?", search);

        List<Object[]> brnchTrgtList = em.createNativeQuery(brnchTrgtScript)
                .setParameter("monthDt", monthDt)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<MwBrnchTrgtDTO> dtoList = new ArrayList();
        brnchTrgtList.forEach(l -> {
            MwBrnchTrgtDTO dto = new MwBrnchTrgtDTO();
            dto.brnchTargetsSeq = l[0] == null ? 0 : new BigDecimal(l[0].toString()).longValue();
            dto.trgtYr = l[1] == null ? 0 : new BigDecimal(l[1].toString()).longValue();
            dto.trgtPerd = l[2] == null ? 0 : new BigDecimal(l[2].toString()).longValue();
            dto.trgtClients = l[3] == null ? 0 : new BigDecimal(l[3].toString()).longValue();
            dto.trgtAmt = l[4] == null ? 0 : new BigDecimal(l[4].toString()).longValue();
            dto.brnchSeq = l[5] == null ? 0 : new BigDecimal(l[5].toString()).longValue();
            dto.brnchNm = l[6] == null ? "" : l[6].toString();
            dto.prdSeq = l[7] == null ? 0 : new BigDecimal(l[7].toString()).longValue();
            dto.prdNm = l[8] == null ? "" : l[8].toString();

            dtoList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("BrnchTrgt", dtoList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    em.createNativeQuery(brnchTrgtCountScript)
                            .setParameter("monthDt", monthDt)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getRegionWiseOutreach(String monthDt, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String outreachScript = " SELECT RWO.OUTREACH_MONTH, RWO.OPENING, RWO.TARGETS, RWO.MATURING_LOANS, \n" +
                " RWO.CLOSING, RWO.REGION_CD, REG.REG_NM FROM REGION_WISE_OUTREACH RWO \n" +
                " JOIN MW_REG REG ON REG.REG_SEQ = RWO.REGION_CD AND REG.CRNT_REC_FLG = 1\n" +
                " WHERE TRUNC(RWO.OUTREACH_MONTH) \n" +
                " = LAST_DAY(TO_DATE(:monthDt, 'dd-mm-rrrr')) ? ORDER BY RWO.TRANS_DATE DESC ";

        String outreachCountScript = "SELECT COUNT(*) FROM ( " + outreachScript + " ) ";
        String search = " ";
        if (filter != null && filter.length() > 0) {
            search = (" AND (LOWER(REG.REG_NM) LIKE '%?%' ) ")
                    .replace("?", filter.toLowerCase());
        }
        outreachScript = outreachScript.replace("?", search);
        outreachCountScript = outreachCountScript.replace("?", search);

        List<Object[]> outreachList = em.createNativeQuery(outreachScript)
                .setParameter("monthDt", monthDt)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<RegionWiseOutreachDTO> dtoList = new ArrayList();
        outreachList.forEach(l -> {
            RegionWiseOutreachDTO dto = new RegionWiseOutreachDTO();
            dto.outreachMonth = l[0] == null ? "" : l[0].toString();
            dto.opening = l[1] == null ? 0 : new BigDecimal(l[1].toString()).longValue();
            dto.targets = l[2] == null ? 0 : new BigDecimal(l[2].toString()).longValue();
            dto.maturingLoans = l[3] == null ? 0 : new BigDecimal(l[3].toString()).longValue();
            dto.closing = l[4] == null ? 0 : new BigDecimal(l[4].toString()).longValue();
            dto.regionCd = l[5] == null ? 0 : new BigDecimal(l[5].toString()).longValue();
            dto.regNm = l[6] == null ? "" : l[6].toString();

            dtoList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Outreach", dtoList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    em.createNativeQuery(outreachCountScript)
                            .setParameter("monthDt", monthDt)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    public Integer addMwBrnchTrgt(MwBrnchTrgtDTO dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

//        if(dto.hrTrvlngSeq != null){
//            MwHrTrvlng trvlng = mwHrTrvlngRepository.findOneByHrTrvlngSeqAndCrntRecFlg(dto.hrTrvlngSeq, true);
//            if(trvlng != null){
//                trvlng.setCrntRecFlg(false);
//                trvlng.setLastUpdBy( currUser);
//                trvlng.setLastUpdDt( currIns );
//
//                mwHrTrvlngRepository.save(trvlng);
//            }
//        }

        MwBrnchTrgt mwBrnchTrgt = new MwBrnchTrgt();
        long brnchTrgtSeq = SequenceFinder.findNextVal(Sequences.BRNCH_TARGETS_SEQ);

        mwBrnchTrgt.setBrnchTargetsSeq(brnchTrgtSeq);
        mwBrnchTrgt.setTrgtYr(dto.trgtYr);
        mwBrnchTrgt.setTrgtPerd(dto.trgtPerd);
        mwBrnchTrgt.setTrgtClients(dto.trgtClients);
        mwBrnchTrgt.setTrgtAmt(dto.trgtAmt);
        mwBrnchTrgt.setBrnchSeq(dto.brnchSeq);
        mwBrnchTrgt.setEffStartDt(currIns);
        mwBrnchTrgt.setPrdSeq(dto.prdSeq);
        mwBrnchTrgt.setCrtdBy(currUser);
        mwBrnchTrgt.setCrtdDt(currIns);
        mwBrnchTrgt.setDelFlg(false);

        mwBrnchTrgtRepository.save(mwBrnchTrgt);

        return 1;
    }

    public Integer addRegionWiseOutreach(RegionWiseOutreachDTO dto, String currUser) throws ParseException {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

        RegionWiseOutreach regionWiseOutreach = new RegionWiseOutreach();

        regionWiseOutreach.setOutreachMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(dto.outreachMonth).toInstant());
        regionWiseOutreach.setOpening(dto.opening);
        regionWiseOutreach.setTargets(dto.targets);
        regionWiseOutreach.setMaturingLoans(dto.maturingLoans);
        regionWiseOutreach.setClosing(dto.closing);
        regionWiseOutreach.setTransDate(currIns);
        regionWiseOutreach.setRegionCd(dto.regionCd);

        regionWiseOutreachRepository.save(regionWiseOutreach);

        return 1;
    }

    public Integer deleteMwBrnchTrgt(Long seq, String currUser) {
        Instant currIns = Instant.now();

        if (seq != null) {
            MwBrnchTrgt brnchTrgt = mwBrnchTrgtRepository.findOneByBrnchTargetsSeqAndDelFlg(seq, false);
            if (brnchTrgt != null) {
                brnchTrgt.setDelFlg(true);

                mwBrnchTrgtRepository.save(brnchTrgt);
                return 1;
            }
        }
        return 0;
    }

    public Integer updateMwBrnchTrgt(MwBrnchTrgtDTO dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto != null) {
            MwBrnchTrgt mwBrnchTrgt = mwBrnchTrgtRepository.findOneByBrnchTargetsSeqAndDelFlg(dto.brnchTargetsSeq, false);
            if (mwBrnchTrgt != null) {
                mwBrnchTrgt.setTrgtYr(dto.trgtYr);
                mwBrnchTrgt.setTrgtPerd(dto.trgtPerd);
                mwBrnchTrgt.setTrgtClients(dto.trgtClients);
                mwBrnchTrgt.setTrgtAmt(dto.trgtAmt);
                mwBrnchTrgt.setBrnchSeq(dto.brnchSeq);
                mwBrnchTrgt.setPrdSeq(dto.prdSeq);

                mwBrnchTrgtRepository.save(mwBrnchTrgt);
                return 1;
            }
        }
        return 0;
    }

    public Integer updateRegionWiseOutreach(RegionWiseOutreachDTO dto, String currUser) throws ParseException {
        Instant currIns = Instant.now();

        if (dto != null) {
            RegionWiseOutreach regionWiseOutreach = regionWiseOutreachRepository.findOneByRegionCdAndOutreachMonth(dto.outreachMonth, dto.regionCd);
            if (regionWiseOutreach != null) {
                regionWiseOutreach.setOutreachMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(dto.outreachMonth).toInstant());
                regionWiseOutreach.setOpening(dto.opening);
                regionWiseOutreach.setTargets(dto.targets);
                regionWiseOutreach.setMaturingLoans(dto.maturingLoans);
                regionWiseOutreach.setClosing(dto.closing);
                regionWiseOutreach.setRegionCd(dto.regionCd);

                regionWiseOutreachRepository.save(regionWiseOutreach);
                return 1;
            }
        }
        return 0;
    }
}
