package com.idev4.admin.service;

import com.idev4.admin.domain.MwSancList;
import com.idev4.admin.repository.ClientRepository;
import com.idev4.admin.repository.MwSancListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.*;

/**
 * Service Implementation for managing MwNactaList.
 */
@Service
@Transactional
public class MwNactaListService {

    private final Logger log = LoggerFactory.getLogger(MwNactaListService.class);

    private final EntityManager entityManager;
    // Added by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
    private final MwSancListRepository mwSancListRepository;

    //static List<MwNactaList> nactaList = new ArrayList<>();
    @Autowired
    ClientRepository clientRepository;
    private RestTemplate restTemplate = new RestTemplate();
    private int batchSize = 500;
    private List<MwSancList> sancLists = new ArrayList<>();
    // End by Zohaib Asim

    public MwNactaListService(EntityManager entityManager, MwSancListRepository mwSancListRepository) {
        this.entityManager = entityManager;
        this.mwSancListRepository = mwSancListRepository;
    }

    /*@Transactional(readOnly = true)
    public Map<String, Object> findAllNactaList(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get All mwNactList : {}");

        String ruleScript = "SELECT li.* FROM MW_NACTA_LIST li ";
        String ruleCountScript = "SELECT count(*) FROM MW_NACTA_LIST li ";

        if (filter != null && filter.length() > 0) {
            String search = ("where (li.NACTA_LIST_SEQ LIKE '%?%' ) OR (li.CNIC_NUM LIKE '%?%') OR ( LOWER(li.DIST) LIKE '%?%') " +
                    " OR ( LOWER(li.FTHR_NM) LIKE '%?%') OR ( LOWER(li.CLNT_NM) LIKE '%?%') OR ( LOWER(li.PRVNC) LIKE '%?%')")
                    .replace("?", filter.toLowerCase());

            ruleScript += search;
            ruleCountScript += search;
        }

        List<MwNactaList> allRulesList = entityManager.createNativeQuery(ruleScript + "\r\n order by 1 Desc", MwNactaList.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("nactaList", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                    entityManager.createNativeQuery(ruleCountScript)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }*/

    // Modified by Zohaib Asim - Dated 29-07-2021 CR: Sanction List
    public MwSancList save(MwSancList mwSancList) {
        log.debug("Request to save MwSancList : {}", mwSancList);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwSancList mwSancListResp = new MwSancList();

        if (mwSancList != null) {
            // Validate CNIC
            if (mwSancList.getCnicNum() != null && mwSancList.getCnicNum().length() == 13) {
                mwSancList.setIsValidCnic(1L);
            } else {
                mwSancList.setIsValidCnic(0L);
            }

            // Sanction Type
            if (mwSancList.getSancType().equals("Nacta")) {
                mwSancList.setSancType("Nacta");
            } else {
                mwSancList.setSancType("S-" + mwSancList.getSancType());
            }

            mwSancList.setIsMtchFound(0L);
            mwSancList.setCrntRecFlg(1L);
            mwSancList.setProcesdRecFlg(0L);
            mwSancList.setTagClntFlg(0L);
            mwSancList.setDelFlg(0L);
            mwSancList.setCrtdBy(currUser);
            mwSancList.setCrtdDt(new Date());

            mwSancListResp = mwSancListRepository.save(mwSancList);
        }

        return mwSancListResp;
    }

    // Modified by Zohaib Asim - Dated 29-07-2021 CR: Sanction List
    public boolean delete(Long seq) {
        boolean flag = false;
        MwSancList list = mwSancListRepository.findBySancSeq(seq);
        if (list != null) {
            mwSancListRepository.delete(list);
            flag = true;
        }
        return flag;
    }

    public int update(MwSancList mwSancList) {

        /*if (isClientExits(mwSancList.getCnicNum())) {
            if (mwSancListRepository.findAllByCnicNumAndFrstNmAndLastNmAndFatherNmAndDstrctAndPrvnceAndCntry
                    (mwSancList.getCnicNum(),
                            mwSancList.getFrstNm(),
                            mwSancList.getLastNm(),
                            mwSancList.getFatherNm(),
                            mwSancList.getDstrct(),
                            mwSancList.getPrvnce(),
                            mwSancList.getCntry()) != null) {
                return 0;
            }
        }*/
        MwSancList list = mwSancListRepository.findBySancSeq(mwSancList.getSancSeq());
        if (list != null) {
            list.setSancSeq(mwSancList.getSancSeq());
            list.setCnicNum(mwSancList.getCnicNum() != null ? mwSancList.getCnicNum().trim() : "");
            list.setFatherNm(mwSancList.getFatherNm() != null ? mwSancList.getFatherNm().trim() : "");
            list.setFrstNm(mwSancList.getFrstNm() != null ? mwSancList.getFrstNm().trim() : "");
            list.setLastNm(mwSancList.getLastNm() != null ? mwSancList.getLastNm().trim() : "");
            list.setDstrct(mwSancList.getDstrct() != null ? mwSancList.getDstrct().trim() : "");
            list.setPrvnce(mwSancList.getPrvnce() != null ? mwSancList.getPrvnce().trim() : "");
            list.setCntry(mwSancList.getCntry() != null ? mwSancList.getCntry().trim() : "");
            list.setDob(mwSancList.getDob() != null ? mwSancList.getDob() : null);

            // Validate CNIC
            if (mwSancList.getCnicNum() != null && mwSancList.getCnicNum().length() == 13) {
                list.setIsValidCnic(1L);
            } else {
                list.setIsValidCnic(0L);
            }
            list.setProcesdRecFlg(0L);
            list.setTagClntFlg(0L);

            // Added by Zohaib Asim - Dated 22-2-22 - to save sanction list type
            if (mwSancList.getSancType().equals("Nacta")) {
                list.setSancType(mwSancList.getSancType());
            } else {
                list.setSancType("S-" + mwSancList.getSancType());
            }

            mwSancListRepository.save(list);
            return 1;
        }
        return -1;
    }


    /*
     * Modified by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
     * MwNactaList -> MwSancList
     * */
    public boolean isClientExits(String cnicNum) {
        List<MwSancList> mwSancLists = new ArrayList<>();

        if (cnicNum == null)
            return false;

        mwSancLists = mwSancListRepository.findAllByCnicNumAndCrntRecFlg(cnicNum.trim(), 1L);

        if (mwSancLists.size() > 0) {
            return true;
        }
        return false;
    }

    /*
     * Modified by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
     * MwNactaList -> MwSancList
     * */
    public String makeDataSet(List<MwSancList> sancLists) {
        List<MwSancList> listsRecord = new ArrayList<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        int recCount = 0;

        for (int i = 0; i < sancLists.size(); i++) {
            MwSancList li = sancLists.get(i);
            //li.setNactaListSeq(id++);
            if (!isClientExits(li.getCnicNum())) {
                //
                if ((li.getCnicNum() != null && li.getCnicNum().trim().length() == 13) &&
                        (li.getCntry() != null && li.getCntry().toUpperCase().equals("PAKISTAN"))) {
                    li.setIsValidCnic(1L);
                } else {
                    li.setIsValidCnic(0L);
                }

                //
                if (li.getSancType() != null &&
                        !li.getSancType().toUpperCase().equals("NACTA")) {
                    li.setSancType("S-" + li.getSancType());
                }

                li.setIsMtchFound(0L);
                li.setCrntRecFlg(1L);
                li.setProcesdRecFlg(0L);
                li.setTagClntFlg(0L);
                li.setDelFlg(0L);
                li.setCrtdBy(currUser);
                li.setCrtdDt(new Date());

                listsRecord.add(li);

                if (i % batchSize == 0 && i > 0) {
                    mwSancListRepository.save(listsRecord);
                    listsRecord.clear();
                }
            }
            recCount++;
        }
        if (listsRecord.size() > 0) {
            mwSancListRepository.save(listsRecord);
            listsRecord.clear();
        }
        //mwSancListRepository.save(listsRecord);

        return "File Uploaded Successfully with Records: " + recCount + "/" + sancLists.size();
    }

    public void updateRepository() throws Exception {
        final List<Map<String, ?>> lists;

        try {
            lists = (List<Map<String, ?>>) restTemplate.getForObject("https://nfs.punjab.gov.pk/Home/GetJosn", Object.class);
            insertDetail(lists);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new Exception();
        }
    }

    /*
     * Modified by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
     * Updating table by getting latest Nacta List from URL
     */
    @Transactional
    public void insertDetail(List<Map<String, ?>> lists) throws Exception {
        Map<String, ?> map;
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch All Nacta List to Delete
        if (lists.size() > 0 && lists != null) {
            try {
                // Fetch All Nacta List to Delete
                List<MwSancList> mwSancList = mwSancListRepository.findAllBySancType("Nacta");

                for (int i = 0; i < mwSancList.size(); i++) {
                    sancLists.add(mwSancList.get(i));

                    if (i % batchSize == 0 && i > 0) {
                        mwSancListRepository.deleteInBatch(sancLists);
                        sancLists.clear();
                    }
                }
                if (sancLists.size() > 0) {
                    mwSancListRepository.deleteInBatch(sancLists);
                    sancLists.clear();
                }
            } catch (StackOverflowError sofe) {
                sofe.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < lists.size(); i++) {
            map = lists.get(i);
            MwSancList mwSancList = new MwSancList();

            String cnicNum = map.get("cnic").toString().trim();
            String[] clntNmArr = map.get("name").toString().trim().split(" ");
            String firstNm = "", lastNm = "";
            if (clntNmArr.length > 2) {
                firstNm = map.get("name").toString().trim().substring(0, map.get("name").toString().trim().indexOf(' '));
                lastNm = map.get("name").toString().trim().substring(map.get("name").toString().trim().indexOf(' ') + 1);
            } else if (clntNmArr.length > 1) {
                firstNm = clntNmArr[0];
                lastNm = clntNmArr[1];
            } else {
                firstNm = clntNmArr[0];
                lastNm = " ";
            }

            mwSancList.setFrstNm(firstNm);
            mwSancList.setLastNm(lastNm);
            mwSancList.setFatherNm(map.get("fatherName").toString().trim());
            mwSancList.setCnicNum(cnicNum);
            mwSancList.setDstrct(map.get("district").toString().trim());
            mwSancList.setPrvnce(map.get("province").toString().trim());
            mwSancList.setCntry("Pakistan");
            mwSancList.setSancType("Nacta");

            mwSancList.setIsValidCnic(cnicNum.length() == 13 ? 1L : 0L);
            mwSancList.setIsMtchFound(0L);
            mwSancList.setCrntRecFlg(1L);
            mwSancList.setDelFlg(0L);
            mwSancList.setProcesdRecFlg(0L);
            mwSancList.setTagClntFlg(0L);
            mwSancList.setCrtdBy(currUser);
            mwSancList.setCrtdDt(new Date());
            sancLists.add(mwSancList);

            if (i % batchSize == 0 && i > 0) {
                mwSancListRepository.save(sancLists);
                sancLists.clear();
            }
        }
        if (sancLists.size() > 0) {
            mwSancListRepository.save(sancLists);
            sancLists.clear();
        }
    }

    // Added by Zohaib Asim - Dated 19-07-2021 - CR: Sanction List
    @Transactional(readOnly = true)
    public Map<String, Object> findAllSancList(String fileType, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get All findAllSancList : {}");

        String whereSancType = "";
        if (fileType.toUpperCase().equals("NACTA")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) = '" + fileType.toLowerCase() + "' ";
        } else if (fileType.toUpperCase().equals("SANCTION")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) LIKE '%s-%'";
        }

        String ruleScript = "SELECT SL.* FROM MW_SANC_LIST SL WHERE SL.CRNT_REC_FLG = 1 " + whereSancType;
        String ruleCountScript = "SELECT COUNT(*) FROM MW_SANC_LIST SL WHERE SL.CRNT_REC_FLG = 1 " + whereSancType;

        if (filter != null && filter.length() > 0) {
            String search = (" AND ((SL.SANC_SEQ LIKE '%?%' ) OR (SL.CNIC_NUM LIKE '%?%') " +
                    " OR (SL.NATIONAL_ID LIKE '%?%') OR ( LOWER(SL.FATHER_NM) LIKE '%?%')" +
                    " OR ( LOWER(SL.FIRST_NM) LIKE '%?%') OR ( LOWER(SL.LAST_NM) LIKE '%?%')" +
                    "  OR ( LOWER(SL.PRVNCE) LIKE '%?%') OR ( LOWER(SL.DSTRCT) LIKE '%?%')" +
                    "  OR ( LOWER(SL.CNTRY) LIKE '%?%') OR ( LOWER(SL.SANC_TYPE) LIKE '%?%'))")
                    .replace("?", filter.toLowerCase());

            ruleScript += search;
            ruleCountScript += search;
        }

        List<MwSancList> allRulesList = entityManager.createNativeQuery(ruleScript + "\r\n order by 1 Desc", MwSancList.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("SancList", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                    entityManager.createNativeQuery(ruleCountScript)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    // Added by Zohaib Asim - Dated 27-07-2021 - CR: Sanction List
    @Transactional(readOnly = true)
    public Map<String, Object> getAllInValidData(String fileType, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get All getAllInValidData : {}");

        String whereSancType = "";
        if (fileType.toUpperCase().equals("NACTA")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) = '" + fileType.toLowerCase() + "' ";
        } else if (fileType.toUpperCase().equals("SANCTION")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) LIKE '%s-%'";
        }

        String ruleScript = "SELECT SL.* FROM MW_SANC_LIST SL WHERE UPPER(SL.CNTRY) = 'PAKISTAN' " +
                " AND SL.CRNT_REC_FLG = 1 AND SL.IS_VALID_CNIC = 0 " + whereSancType;
        String ruleCountScript = "SELECT COUNT(*) FROM MW_SANC_LIST SL WHERE UPPER(SL.CNTRY) = 'PAKISTAN' " +
                " AND SL.CRNT_REC_FLG = 1 AND SL.IS_VALID_CNIC = 0 " + whereSancType;

        if (filter != null && filter.length() > 0) {
            String search = (" AND ((SL.SANC_SEQ LIKE '%?%' ) OR (SL.CNIC_NUM LIKE '%?%') " +
                    " OR (SL.NATIONAL_ID LIKE '%?%') OR ( LOWER(SL.FATHER_NM) LIKE '%?%')" +
                    " OR ( LOWER(SL.FIRST_NM) LIKE '%?%') OR ( LOWER(SL.LAST_NM) LIKE '%?%')" +
                    "  OR ( LOWER(SL.PRVNCE) LIKE '%?%') OR ( LOWER(SL.DSTRCT) LIKE '%?%')" +
                    "  OR ( LOWER(SL.CNTRY) LIKE '%?%') OR ( LOWER(SL.SANC_TYPE) LIKE '%?%'))")
                    .replace("?", filter.toLowerCase());

            ruleScript += search;
            ruleCountScript += search;
        }

        List<MwSancList> allRulesList = entityManager.createNativeQuery(ruleScript + "\r\n order by 1 Desc", MwSancList.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("InValidData", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                    entityManager.createNativeQuery(ruleCountScript)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    // Find Match
    public String findMatchedClients(String fileType) {
        String parmOutputProcedure = "";
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Precedure Call
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PRC_SANC_LIST");
        storedProcedure.registerStoredProcedureParameter("P_SANC_TYPE", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("OP_PRC_RESP", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("P_SANC_TYPE", fileType);
        storedProcedure.execute();

        parmOutputProcedure = storedProcedure.getOutputParameterValue("OP_PRC_RESP").toString();
        if (parmOutputProcedure.contains("SUCCESS")) {
            log.info("PRC_SANC_LIST: Successfully Executed.");
        } else {
            log.info("PRC_SANC_LIST: Execution unSuccessfully.");
        }

        return parmOutputProcedure;
    }

    public Map<String, Object> getMatchedClients(String fileType) {
        Map<String, Object> mapResp = new HashMap<>();

        String parmSancType = "";
        if (fileType.equals("Sanction")) {
            parmSancType = "S-%";
        } else {
            parmSancType = "Nacta";
        }

        List<MwSancList> mtchdSancList =
                mwSancListRepository.findAllByCrntRecFlgAndIsMtchFoundAndSancTypeLike(1L, 1L, parmSancType);
        mapResp.put("MatchedClients", mtchdSancList);

        return mapResp;
    }

    // Delete InValid Data
    @Transactional
    public Map<String, Object> deleteAllInValidData(String fileType) {
        Map<String, Object> mapResp = new HashMap<>();
        int entriesCount = 0;
        String whereSancType = "";

        if (fileType.toUpperCase().equals("NACTA")) {
            whereSancType = "Nacta";
        } else if (fileType.toUpperCase().equals("SANCTION")) {
            whereSancType = "S-%";
        }

        List<MwSancList> mwSancLists = mwSancListRepository.findAllByCrntRecFlgAndIsValidCnicAndCntryAndSancTypeLike(
                1L, 0L, "Pakistan", whereSancType
        );
        entriesCount = mwSancLists.size();
        try {
            for (int i = 0; i < mwSancLists.size(); i++) {
                sancLists.add(mwSancLists.get(i));

                if (i % batchSize == 0 && i > 0) {
                    mwSancListRepository.deleteInBatch(sancLists);
                    sancLists.clear();
                }
            }
            if (sancLists.size() > 0) {
                mwSancListRepository.deleteInBatch(sancLists);
                sancLists.clear();
            }
        } catch (StackOverflowError sofe) {
            sofe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mapResp.put("DeleteData", entriesCount + " entries has been deleted from " + fileType);

        return mapResp;
    }

    @Transactional
    public Map<String, Object> findMtchAndTagClnt(String tagDesc, Long cnic) {
        Map<String, Object> mapResp = new HashMap<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        //Clients clients = clientRepository.findAllByCrntRecFlgAndCnicNum(true, cnic);
        List<MwSancList> mwSancList = mwSancListRepository.findAllByCnicNumAndCrntRecFlg(cnic.toString(), 1L);

        if (cnic > 0) {
            // Precedure Call
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PRC_TAG_CLNTS");
            storedProcedure.registerStoredProcedureParameter("P_TAG_DTL", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_LOAN_APP_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_CNIC", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_TAGS_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_RMKS", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_USER_ID", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("OP_PRC_RESP", String.class, ParameterMode.OUT);

            storedProcedure.setParameter("P_TAG_DTL", tagDesc.toUpperCase());
            storedProcedure.setParameter("P_LOAN_APP_SEQ", 0L);
            storedProcedure.setParameter("P_CNIC", cnic);
            storedProcedure.setParameter("P_TAGS_SEQ", 6L);
            storedProcedure.setParameter("P_RMKS", "AML TAGGED THROUGH NACTA MANAGEMENT");
            storedProcedure.setParameter("P_USER_ID", currUser);
            storedProcedure.execute();

            String parmOutputProcedure = storedProcedure.getOutputParameterValue("OP_PRC_RESP").toString();
            if (parmOutputProcedure.contains("SUCCESS")) {
                log.info("PRC_TAG_CLNTS: Successfully Executed.");
                mapResp.put("Response", "SUCCESS");
            } else {
                log.info("PRC_TAG_CLNTS: Execution unSuccessfully.");
                mapResp.put("Response", "FAILED");
            }
        } else {
            mapResp.put("Response", "FAILED");
        }

        return mapResp;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllTaggedClnts(String fileType, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get All getAllInValidData : {}");

        String whereSancType = "";
        if (fileType.toUpperCase().equals("NACTA")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) = '" + fileType.toLowerCase() + "' ";
        } else if (fileType.toUpperCase().equals("SANCTION")) {
            whereSancType = " AND LOWER(SL.SANC_TYPE) LIKE '%s-%'";
        }

        String ruleScript = "SELECT SL.* FROM MW_SANC_LIST SL WHERE UPPER(SL.CNTRY) = 'PAKISTAN' " +
                " AND SL.CRNT_REC_FLG = 1 AND SL.TAG_CLNT_FLG = 1 " + whereSancType;
        String ruleCountScript = "SELECT COUNT(*) FROM MW_SANC_LIST SL WHERE UPPER(SL.CNTRY) = 'PAKISTAN' " +
                " AND SL.CRNT_REC_FLG = 1 AND SL.TAG_CLNT_FLG = 1 " + whereSancType;

        if (filter != null && filter.length() > 0) {
            String search = (" AND ((SL.SANC_SEQ LIKE '%?%' ) OR (SL.CNIC_NUM LIKE '%?%') " +
                    " OR (SL.NATIONAL_ID LIKE '%?%') OR ( LOWER(SL.FATHER_NM) LIKE '%?%')" +
                    " OR ( LOWER(SL.FIRST_NM) LIKE '%?%') OR ( LOWER(SL.LAST_NM) LIKE '%?%')" +
                    "  OR ( LOWER(SL.PRVNCE) LIKE '%?%') OR ( LOWER(SL.DSTRCT) LIKE '%?%')" +
                    "  OR ( LOWER(SL.CNTRY) LIKE '%?%') OR ( LOWER(SL.SANC_TYPE) LIKE '%?%'))")
                    .replace("?", filter.toLowerCase());

            ruleScript += search;
            ruleCountScript += search;
        }

        List<MwSancList> allRulesList = entityManager.createNativeQuery(ruleScript + "\r\n order by 1 Desc", MwSancList.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("TaggedClnts", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                    entityManager.createNativeQuery(ruleCountScript)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> getSancCountriesList() {
        List<Map<String, String>> response = new ArrayList<>();
        Query qry = entityManager.createNativeQuery("SELECT SCV.REF_CD_VAL_DSCR FROM MW_STP_CNFIG_VAL SCV \n" +
                "WHERE SCV.CRNT_REC_FLG = 1 AND SCV.STP_GRP_CD = '0006' AND SCV.STP_VAL_CD = '0001'");
        List sancCountriesList = qry.getResultList();

        String[] splitList;
        if (sancCountriesList.size() > 0) {
            splitList = sancCountriesList.get(0).toString().split(",");

            for (String strObj : splitList) {
                Map<String, String> mapObj = new HashMap<>();
                mapObj.put("id", strObj);
                mapObj.put("value", strObj);
                response.add(mapObj);
            }
        }

        return response;
    }
    /*public Map<String, Object> getSancCountriesList() {
        Map<String, Object> response = new HashMap<>();
        Query qry = entityManager.createNativeQuery("SELECT SCV.REF_CD_VAL_DSCR FROM MW_STP_CNFIG_VAL SCV \n" +
                "WHERE SCV.CRNT_REC_FLG = 1 AND SCV.STP_GRP_CD = '0006' AND SCV.STP_VAL_CD = '0001'");
        List sancCountriesList = qry.getResultList();
        String[] strList;
        if ( sancCountriesList.size() > 0 ){
            response.put("CntryList", sancCountriesList.get(0));
        }


        return response;
    }*/
    // End by Zohaib Asim
}
