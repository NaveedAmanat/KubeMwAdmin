package com.idev4.admin.service;

import com.idev4.admin.domain.MwPnlHsptls;
import com.idev4.admin.domain.MwPnlHsptlsRel;
import com.idev4.admin.dto.MwPnlHsptlsDTO;
import com.idev4.admin.repository.MwPnlHsptlsRelRepository;
import com.idev4.admin.repository.MwPnlHsptlsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

/*
Authored by Areeba
Dated 14-3-2022
Jubliee Panel Hospital List for KSZB clients
*/

@Service
@Transactional
public class PnlHsptlsService {

    private final Logger log = LoggerFactory.getLogger(PnlHsptlsService.class);
    private final EntityManager entityManager;

    private final MwPnlHsptlsRepository mwPnlHsptlsRepository;
    private final MwPnlHsptlsRelRepository mwPnlHsptlsRelRepository;


    private List<MwPnlHsptlsRel> mwPnlHsptlsRelList = new ArrayList<>();
    private List<MwPnlHsptls> pnlHsptls = new ArrayList<>();
    private int batchSize = 500;

    public PnlHsptlsService(EntityManager entityManager, MwPnlHsptlsRepository mwPnlHsptlsRepository, MwPnlHsptlsRelRepository mwPnlHsptlsRelRepository) {
        this.entityManager = entityManager;
        this.mwPnlHsptlsRepository = mwPnlHsptlsRepository;
        this.mwPnlHsptlsRelRepository = mwPnlHsptlsRelRepository;
    }

    @Transactional(readOnly = true)
    public List<MwPnlHsptls> findAllByCurrentRecord() {
        log.debug("Request to get all Hospitals");
        return mwPnlHsptlsRepository.getAllHospitals();
    }

    @Transactional(readOnly = true)
    public List<MwPnlHsptls> findAllNonBlacklistRecord() {
        log.debug("Request to get all Hospitals");
        return mwPnlHsptlsRepository.getAllNBlacklistHospitals();
    }

    public boolean delete(Long seq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean flag = false;
        if (seq == null)
            return flag;
        MwPnlHsptlsRel rel = mwPnlHsptlsRelRepository.findByIdAndCrntRecFlg(seq, 1L);
        if (rel != null) {
            rel.setCrntRecFlg(0L);
            rel.setDelFlg(1L);
            rel.setLastUpdDt(new Date());
            rel.setLastUpdBy(currUser);
            mwPnlHsptlsRelRepository.save(rel);
            flag = true;
        }
        return flag;
    }

    public boolean deleteHsptl(Long seq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean flag = false;
        if (seq == null)
            return flag;
        MwPnlHsptls rel = mwPnlHsptlsRepository.findByIdAndCrntRecFlg(seq, 1L);
        if (rel != null) {
            rel.setCrntRecFlg(0L);
            rel.setDelFlg(1L);
            rel.setLastUpdDt(new Date());
            rel.setLastUpdBy(currUser);
            mwPnlHsptlsRepository.save(rel);
            flag = true;
        } else {
            return flag;
        }

        int entriesCount = 0;
        List<MwPnlHsptlsRel> rel2 = mwPnlHsptlsRelRepository.findAllByHsptlsIdAndCrntRecFlg(seq, 1L);
        //entriesCount = rel2.size();

        for (MwPnlHsptlsRel hsprel : rel2) {
            if (hsprel != null) {
                hsprel.setCrntRecFlg(0L);
                hsprel.setDelFlg(1L);
                hsprel.setLastUpdDt(new Date());
                hsprel.setLastUpdBy(currUser);
                mwPnlHsptlsRelRepository.save(hsprel);
                flag = true;
            } else {
                flag = false;
            }
        }
//        try {
//            for (int i = 0; i < rel2.size(); i++) {
//                mwPnlHsptlsRelList.add(rel2.get(i));
//
//                if (i % batchSize == 0 && i > 0) {
//                    mwPnlHsptlsRelRepository.deleteInBatch(mwPnlHsptlsRelList);
//                    mwPnlHsptlsRelList.clear();
//                }
//            }
//            if (mwPnlHsptlsRelList.size() > 0) {
//                mwPnlHsptlsRelRepository.deleteInBatch(mwPnlHsptlsRelList);
//                mwPnlHsptlsRelList.clear();
//            }
//            flag = true;
//        } catch (StackOverflowError sofe) {
//            sofe.printStackTrace();
//            flag = false;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            flag = false;
//        }

        return flag;
    }

    @Transactional
    public int deleteAll() {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int entriesCount = 0;
        List<MwPnlHsptlsRel> rel = mwPnlHsptlsRelRepository.findAllByCrntRecFlg(1L);
        entriesCount = rel.size();

        for (MwPnlHsptlsRel hsprel : rel) {
            if (hsprel != null) {
                hsprel.setCrntRecFlg(0L);
                hsprel.setDelFlg(1L);
                hsprel.setLastUpdDt(new Date());
                hsprel.setLastUpdBy(currUser);
                mwPnlHsptlsRelRepository.save(hsprel);
            }
        }
//        try {
//            for (int i = 0; i < rel.size(); i++) {
//                mwPnlHsptlsRelList.add(rel.get(i));
//
//                if (i % batchSize == 0 && i > 0) {
//                    mwPnlHsptlsRelRepository.deleteInBatch(mwPnlHsptlsRelList);
//                    mwPnlHsptlsRelList.clear();
//                }
//            }
//            if (mwPnlHsptlsRelList.size() > 0) {
//                mwPnlHsptlsRelRepository.deleteInBatch(mwPnlHsptlsRelList);
//                mwPnlHsptlsRelList.clear();
//            }
//        } catch (StackOverflowError sofe) {
//            sofe.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        return entriesCount;
    }

    public int update(MwPnlHsptlsDTO mwPnlHsptlsDTO) {

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwPnlHsptlsDTO.brnchSeq != null) {
            MwPnlHsptlsRel list = mwPnlHsptlsRelRepository.findByBrnchSeqAndHsptlsIdAndCrntRecFlg(mwPnlHsptlsDTO.brnchSeq, mwPnlHsptlsDTO.hsptlsId, 1L);
            if (list != null) {
                list.setDistance(mwPnlHsptlsDTO.distance);
                list.setRemarks(mwPnlHsptlsDTO.remarks != null ? mwPnlHsptlsDTO.remarks.trim() : "");
                list.setLastUpdBy(currUser);
                list.setLastUpdDt(new Date());

                mwPnlHsptlsRelRepository.save(list);
                return 1;
            }
            return -1;
        } else {
            MwPnlHsptls list = mwPnlHsptlsRepository.findByIdAndCrntRecFlg(mwPnlHsptlsDTO.hsptlsId, 1L);
            if (list != null) {
                list.setHsptlsNm(mwPnlHsptlsDTO.hsptlsNm != null ? mwPnlHsptlsDTO.hsptlsNm.trim() : "");
                list.setHsptlsAddr(mwPnlHsptlsDTO.hsptlsAddr != null ? mwPnlHsptlsDTO.hsptlsAddr.trim() : "");
                list.setHsptlsPh(mwPnlHsptlsDTO.hsptlsPh != null ? mwPnlHsptlsDTO.hsptlsPh.trim() : "");
                list.setHsptlsTypSeq(mwPnlHsptlsDTO.hsptlsTypSeq);
                list.setHsptlsStsSeq(mwPnlHsptlsDTO.hsptlsStsSeq);
                list.setLastUpdBy(currUser);
                list.setLastUpdDt(new Date());

                mwPnlHsptlsRepository.save(list);
                return 1;
            }
            return -1;
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllPanelHospitals(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String queryScript = " SELECT PHL.ID, PHL.HSPTLS_NM, PHL.HSPTLS_ADDR, PHL.HSPTLS_PH, PHL.HSPTLS_TYP_SEQ, PHL.HSPTLS_STS_SEQ, PHL.CRTD_DT, PHL.CRTD_BY, PHL.LAST_UPD_DT, PHL.LAST_UPD_BY,   " +
                "                  PHR.ID AS REL_ID, PHR.BRNCH_SEQ, PHR.HSPTLS_ID, PHR.DISTANCE, PHR.REMARKS, PHR.CRTD_DT AS REL_CRTD_DT, PHR.CRTD_BY AS REL_CRTD_BY,   " +
                "                  PHR.LAST_UPD_DT AS REL_UPD_DT, PHR.LAST_UPD_BY AS REL_UPD_BY, BR.BRNCH_NM, " +
                "                  (SELECT RCV.REF_CD_DSCR FROM MW_REF_CD_VAL RCV WHERE RCV.REF_CD_SEQ = PHL.HSPTLS_TYP_SEQ) AS HSPTLS_TYP," +
                "                  (SELECT RCV.REF_CD_DSCR FROM MW_REF_CD_VAL RCV WHERE RCV.REF_CD_SEQ = PHL.HSPTLS_STS_SEQ) AS HSPTLS_STS" +
                "                  FROM MW_PNL_HSPTLS PHL, MW_PNL_HSPTLS_REL PHR, MW_BRNCH BR " +
                "                  WHERE PHL.ID = PHR.HSPTLS_ID AND BR.BRNCH_SEQ = PHR.BRNCH_SEQ " +
                "                  AND PHL.CRNT_REC_FLG = 1 AND PHR.CRNT_REC_FLG = 1 ";

        String queryCount = " SELECT COUNT(*) FROM MW_PNL_HSPTLS PHL, MW_PNL_HSPTLS_REL PHR, MW_BRNCH BR " +
                "                                  WHERE PHL.ID = PHR.HSPTLS_ID AND BR.BRNCH_SEQ = PHR.BRNCH_SEQ " +
                "                                  AND PHL.CRNT_REC_FLG = 1 AND PHR.CRNT_REC_FLG = 1 ";

        if (filter != null && filter.length() > 0) {
            String search = (" AND ( " +
                    " (LOWER(BR.BRNCH_NM) LIKE '%?%') " +
                    " OR (LOWER(PHL.HSPTLS_NM) LIKE '%?%') " +
                    " ) ")
                    .replace("?", filter.toLowerCase());

            queryScript += search;
            queryCount += search;
        }

        List<Object[]> pnlHsptlList = entityManager.createNativeQuery(queryScript + "\r\n order by 6 Asc")
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<MwPnlHsptlsDTO> dtoList = new ArrayList();
        pnlHsptlList.forEach(c -> {
            MwPnlHsptlsDTO dto = new MwPnlHsptlsDTO();
            dto.id = c[0] == null ? 0 : new BigDecimal(c[0].toString()).longValue();
            dto.hsptlsNm = c[1] == null ? "" : c[1].toString();
            dto.hsptlsAddr = c[2] == null ? "" : c[2].toString();
            dto.hsptlsPh = c[3] == null ? "" : c[3].toString();
            dto.hsptlsTypSeq = c[4] == null ? 0 : new BigDecimal(c[4].toString()).longValue();
            dto.hsptlsStsSeq = c[5] == null ? 0 : new BigDecimal(c[5].toString()).longValue();
            dto.crtdDt = c[6] == null ? "" : c[6].toString();
            dto.crtdBy = c[7] == null ? "" : c[7].toString();
            dto.lastUpdDt = c[8] == null ? "" : c[8].toString();
            dto.lastUpdBy = c[9] == null ? "" : c[9].toString();
            dto.relId = c[10] == null ? 0 : new BigDecimal(c[10].toString()).longValue();
            dto.brnchSeq = c[11] == null ? 0 : new BigDecimal(c[11].toString()).longValue();
            dto.hsptlsId = c[12] == null ? 0 : new BigDecimal(c[12].toString()).longValue();
            dto.distance = c[13] == null ? 0 : new BigDecimal(c[13].toString()).doubleValue();
            dto.remarks = c[14] == null ? "" : c[14].toString();
            dto.relCrtdDt = c[15] == null ? "" : c[15].toString();
            dto.relCrtdBy = c[16] == null ? "" : c[16].toString();
            dto.relLastUpdDt = c[17] == null ? "" : c[17].toString();
            dto.relLastUpdBy = c[18] == null ? "" : c[18].toString();
            dto.brnchNm = c[19] == null ? "" : c[19].toString();
            dto.hsptlsTyp = c[20] == null ? "" : c[20].toString();
            dto.hsptlsSts = c[21] == null ? "" : c[21].toString();
            dtoList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Hospitals", dtoList);


        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    entityManager.createNativeQuery(queryCount)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    public int save(MwPnlHsptlsDTO mwPnlHsptlsDTO) {
        log.debug("Request to save MwPnlHsptlsDTO : {}", mwPnlHsptlsDTO);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        //if (mwPnlHsptlsDTO.hsptlsId != null){

        if (mwPnlHsptlsDTO.hsptlsId == null) {
            mwPnlHsptlsDTO.hsptlsId = 0L;
        }
        MwPnlHsptls exists = mwPnlHsptlsRepository.findByIdAndCrntRecFlg(mwPnlHsptlsDTO.hsptlsId, 1L);
        if (exists != null) {
            MwPnlHsptlsRel exists2 = mwPnlHsptlsRelRepository.findByBrnchSeqAndHsptlsId(mwPnlHsptlsDTO.brnchSeq, exists.getId());
            if (exists2 != null) {
                if (exists2.getCrntRecFlg() == 0L) {
                    exists2.setCrntRecFlg(1L);
                    exists2.setDelFlg(0L);
                    exists2.setLastUpdDt(new Date());
                    exists2.setLastUpdBy(currUser);
                    exists2.setDistance(mwPnlHsptlsDTO.distance);
                    exists2.setRemarks(mwPnlHsptlsDTO.remarks);

                    mwPnlHsptlsRelRepository.save(exists2);
                    return 1;
                } else
                    return 0;
            } else {
                if (mwPnlHsptlsDTO.hsptlsStsSeq != null && mwPnlHsptlsDTO.hsptlsStsSeq == 1927) {
                    log.info("Hospital is Blacklisted.");
                    return 2;
                } else {
                    MwPnlHsptlsRel mwPnlHsptlsRel = new MwPnlHsptlsRel();

                    mwPnlHsptlsRel.setBrnchSeq(mwPnlHsptlsDTO.brnchSeq);
                    mwPnlHsptlsRel.setHsptlsId(exists.getId());
                    mwPnlHsptlsRel.setDistance(mwPnlHsptlsDTO.distance);
                    mwPnlHsptlsRel.setRemarks(mwPnlHsptlsDTO.remarks);
                    mwPnlHsptlsRel.setCrntRecFlg(1L);
                    mwPnlHsptlsRel.setDelFlg(0L);
                    mwPnlHsptlsRel.setCrtdDt(new Date());
                    mwPnlHsptlsRel.setCrtdBy(currUser);
                    mwPnlHsptlsRel.setLastUpdDt(new Date());
                    mwPnlHsptlsRel.setLastUpdBy(currUser);

                    mwPnlHsptlsRel = mwPnlHsptlsRelRepository.save(mwPnlHsptlsRel);
                    return 1;
                }
            }
        } else {
            MwPnlHsptls mwPnlHsptls = new MwPnlHsptls();

            mwPnlHsptls.setHsptlsNm(mwPnlHsptlsDTO.hsptlsNm);
            mwPnlHsptls.setHsptlsAddr(mwPnlHsptlsDTO.hsptlsAddr);
            mwPnlHsptls.setHsptlsPh(mwPnlHsptlsDTO.hsptlsPh);
            mwPnlHsptls.setHsptlsTypSeq(mwPnlHsptlsDTO.hsptlsTypSeq);
            mwPnlHsptls.setHsptlsStsSeq(mwPnlHsptlsDTO.hsptlsStsSeq);
            mwPnlHsptls.setCrntRecFlg(1L);
            mwPnlHsptls.setDelFlg(0L);
            mwPnlHsptls.setCrtdDt(new Date());
            mwPnlHsptls.setCrtdBy(currUser);
            mwPnlHsptls.setLastUpdDt(new Date());
            mwPnlHsptls.setLastUpdBy(currUser);

            mwPnlHsptls = mwPnlHsptlsRepository.save(mwPnlHsptls);

            if (mwPnlHsptlsDTO.hsptlsStsSeq != null && mwPnlHsptlsDTO.hsptlsStsSeq == 1927) {
                log.info("Hospital is Blacklisted.");
                return 2;
            } else {
                MwPnlHsptlsRel mwPnlHsptlsRel = new MwPnlHsptlsRel();

                mwPnlHsptlsRel.setBrnchSeq(mwPnlHsptlsDTO.brnchSeq);
                mwPnlHsptlsRel.setHsptlsId(mwPnlHsptls.getId());
                mwPnlHsptlsRel.setDistance(mwPnlHsptlsDTO.distance);
                mwPnlHsptlsRel.setRemarks(mwPnlHsptlsDTO.remarks);
                mwPnlHsptlsRel.setCrntRecFlg(1L);
                mwPnlHsptlsRel.setDelFlg(0L);
                mwPnlHsptlsRel.setCrtdDt(new Date());
                mwPnlHsptlsRel.setCrtdBy(currUser);
                mwPnlHsptlsRel.setLastUpdDt(new Date());
                mwPnlHsptlsRel.setLastUpdBy(currUser);

                mwPnlHsptlsRel = mwPnlHsptlsRelRepository.save(mwPnlHsptlsRel);
                return 1;
            }
        }
    }

    public int saveHsptl(MwPnlHsptlsDTO mwPnlHsptlsDTO) {
        log.debug("Request to save MwPnlHsptlsDTO : {}", mwPnlHsptlsDTO);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPnlHsptls mwPnlHsptls = new MwPnlHsptls();

        mwPnlHsptls.setHsptlsNm(mwPnlHsptlsDTO.hsptlsNm);
        mwPnlHsptls.setHsptlsAddr(mwPnlHsptlsDTO.hsptlsAddr);
        mwPnlHsptls.setHsptlsPh(mwPnlHsptlsDTO.hsptlsPh);
        mwPnlHsptls.setHsptlsTypSeq(mwPnlHsptlsDTO.hsptlsTypSeq);
        mwPnlHsptls.setHsptlsStsSeq(mwPnlHsptlsDTO.hsptlsStsSeq);
        mwPnlHsptls.setCrntRecFlg(1L);
        mwPnlHsptls.setDelFlg(0L);
        mwPnlHsptls.setCrtdDt(new Date());
        mwPnlHsptls.setCrtdBy(currUser);
        mwPnlHsptls.setLastUpdDt(new Date());
        mwPnlHsptls.setLastUpdBy(currUser);

        mwPnlHsptls = mwPnlHsptlsRepository.save(mwPnlHsptls);
        return 1;
    }

    public String makeDataSet(List<MwPnlHsptlsDTO> mwPnlHsptlsDTO) {
        List<MwPnlHsptlsDTO> listsRecord = new ArrayList<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        int recCount = 0;

        for (int i = 0; i < mwPnlHsptlsDTO.size(); i++) {
            MwPnlHsptlsDTO li = mwPnlHsptlsDTO.get(i);

            if (li.hsptlsId == null) {
                li.hsptlsId = 0L;
            }

            MwPnlHsptls exists = mwPnlHsptlsRepository.findById(li.hsptlsId);
            if (exists != null) {
                if (exists.getCrntRecFlg() == 0L) {
                    exists.setCrntRecFlg(1L);
                    exists.setDelFlg(0L);
                    exists.setLastUpdDt(new Date());
                    exists.setLastUpdBy(currUser);
                    mwPnlHsptlsRepository.save(exists);
                }
                MwPnlHsptlsRel exists2 = mwPnlHsptlsRelRepository.findByBrnchSeqAndHsptlsId(li.brnchSeq, li.hsptlsId);
                if (li.hsptlsStsSeq == 1927) {
                    log.info("Hospital is Blacklisted.");
                } else if (exists2 == null) {

                    MwPnlHsptlsRel mwPnlHsptlsRel = new MwPnlHsptlsRel();

                    mwPnlHsptlsRel.setBrnchSeq(li.brnchSeq);
                    mwPnlHsptlsRel.setHsptlsId(li.hsptlsId);
                    mwPnlHsptlsRel.setDistance(li.distance);
                    mwPnlHsptlsRel.setRemarks(li.remarks);
                    mwPnlHsptlsRel.setCrntRecFlg(1L);
                    mwPnlHsptlsRel.setDelFlg(0L);
                    mwPnlHsptlsRel.setCrtdDt(new Date());
                    mwPnlHsptlsRel.setCrtdBy(currUser);
                    mwPnlHsptlsRel.setLastUpdDt(new Date());
                    mwPnlHsptlsRel.setLastUpdBy(currUser);

                    mwPnlHsptlsRel = mwPnlHsptlsRelRepository.save(mwPnlHsptlsRel);
                    //;
                } else {
                    if (exists2.getCrntRecFlg() == 0L) {
                        exists2.setCrntRecFlg(1L);
                        exists2.setDelFlg(0L);
                        exists2.setLastUpdDt(new Date());
                        exists2.setLastUpdBy(currUser);
                        exists2.setDistance(li.distance);
                        exists2.setRemarks(li.remarks);

                        mwPnlHsptlsRelRepository.save(exists2);
                    }
                }
            } else {
                MwPnlHsptls mwPnlHsptls = new MwPnlHsptls();

                mwPnlHsptls.setHsptlsNm(li.hsptlsNm);
                mwPnlHsptls.setHsptlsAddr(li.hsptlsAddr);
                mwPnlHsptls.setHsptlsPh(li.hsptlsPh);
                mwPnlHsptls.setHsptlsTypSeq(li.hsptlsTypSeq);
                mwPnlHsptls.setHsptlsStsSeq(li.hsptlsStsSeq);
                mwPnlHsptls.setCrntRecFlg(1L);
                mwPnlHsptls.setDelFlg(0L);
                mwPnlHsptls.setCrtdDt(new Date());
                mwPnlHsptls.setCrtdBy(currUser);
                mwPnlHsptls.setLastUpdDt(new Date());
                mwPnlHsptls.setLastUpdBy(currUser);

                mwPnlHsptls = mwPnlHsptlsRepository.save(mwPnlHsptls);

                if (li.hsptlsStsSeq == 1927) {
                    log.info("Hospital is Blacklisted.");
                } else {
                    MwPnlHsptlsRel mwPnlHsptlsRel = new MwPnlHsptlsRel();

                    mwPnlHsptlsRel.setBrnchSeq(li.brnchSeq);
                    mwPnlHsptlsRel.setHsptlsId(mwPnlHsptls.getId());
                    //mwPnlHsptlsRel.setHsptlsId(mwPnlHsptlsRepository.getCurrHsptlId());
                    mwPnlHsptlsRel.setDistance(li.distance);
                    mwPnlHsptlsRel.setRemarks(li.remarks);
                    mwPnlHsptlsRel.setCrntRecFlg(1L);
                    mwPnlHsptlsRel.setDelFlg(0L);
                    mwPnlHsptlsRel.setCrtdDt(new Date());
                    mwPnlHsptlsRel.setCrtdBy(currUser);
                    mwPnlHsptlsRel.setLastUpdDt(new Date());
                    mwPnlHsptlsRel.setLastUpdBy(currUser);

                    mwPnlHsptlsRel = mwPnlHsptlsRelRepository.save(mwPnlHsptlsRel);
                }
            }

            recCount++;
        }

        return "File Uploaded Successfully with Records: " + recCount + "/" + mwPnlHsptlsDTO.size();
    }
}
//Ended by Areeba