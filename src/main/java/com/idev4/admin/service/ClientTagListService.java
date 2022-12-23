package com.idev4.admin.service;

import com.idev4.admin.domain.MwClntTagList;
import com.idev4.admin.repository.MwClntTagListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* AUTHOR: Areeba
 *   Dated 1-2-2022
 *   Client Tag List
 */
@Service
@Transactional
public class ClientTagListService {

    private final Logger log = LoggerFactory.getLogger(ClientTagListService.class);
    private final EntityManager entityManager;

    private final MwClntTagListRepository mwClntTagListRepository;
    private List<MwClntTagList> tagLists = new ArrayList<>();

    public ClientTagListService(EntityManager entityManager, MwClntTagListRepository mwClntTagListRepository) {
        this.entityManager = entityManager;
        this.mwClntTagListRepository = mwClntTagListRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllTaggedClnts(String tagType, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        //log.debug("Request to get All getAllInValidData : {}");

        String whereTagType = "";
        if (tagType.toUpperCase().equals("TAG")) {
            whereTagType = " AND CTL.CRNT_REC_FLG = 1 ";
        } else if (tagType.toUpperCase().equals("UNTAG")) {
            whereTagType = " AND CTL.CRNT_REC_FLG = 0 AND CTL.DEL_FLG = 1 ";
        }

        String tagScript = "SELECT CTL.* FROM MW_CLNT_TAG_LIST CTL " +
                "WHERE CTL.TAGS_SEQ = 6 " + whereTagType;
        String tagCountScript = "SELECT COUNT(*) FROM MW_CLNT_TAG_LIST CTL " +
                "WHERE CTL.TAGS_SEQ = 6 " + whereTagType;

        if (filter != null && filter.length() > 0) {
            String search = (" AND ((CTL.CLNT_TAG_LIST_SEQ LIKE '%?%' ) OR (CTL.EFF_START_DT LIKE '%?%') " +
                    " OR (CTL.CNIC_NUM LIKE '%?%') " +
                    " OR ( LOWER(CTL.LAST_UPD_BY) LIKE '%?%') OR (CTL.LAST_UPD_DT LIKE '%?%')" +
                    "  OR (CTL.TAG_FROM_DT LIKE '%?%') OR (CTL.TAG_TO_DT LIKE '%?%')" +
                    "  OR (CTL.LOAN_APP_SEQ LIKE '%?%')) ")
                    .replace("?", filter.toLowerCase());

            tagScript += search;
            tagCountScript += search;
        }

        List<MwClntTagList> allTagsList = entityManager.createNativeQuery(tagScript + "\r\n order by 1 Desc", MwClntTagList.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("TaggedClnts", allTagsList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    entityManager.createNativeQuery(tagCountScript)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    public String changeClntTag(String tagDesc, Long clntTagListSeq) {
        //Map<String, Object> mapResp = new HashMap<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Long oldcrntrecflg;
        Long crntrecflg;
        Long delflg;
        if (tagDesc.equals("TAG")) {
            oldcrntrecflg = 0L;
            crntrecflg = 1L;
            delflg = 0L;
        } else if (tagDesc.equals("UNTAG")) {
            oldcrntrecflg = 1L;
            crntrecflg = 0L;
            delflg = 1L;
        } else {
            return "FAILED";
        }
        MwClntTagList list = mwClntTagListRepository.findAllByClntTagListSeqAndCrntRecFlg(clntTagListSeq, oldcrntrecflg);

        if (list != null) {
            list.setCrntRecFlg(crntrecflg);
            list.setDelFlg(delflg);
            list.setLastUpdBy(currUser);
            list.setLastUpdDt(Instant.now());

            mwClntTagListRepository.save(list);
            //mapResp.put("Response", "SUCCESS");
            return "SUCCESS";
        }
        //mapResp.put("Response", "FAILED");
        return "FAILED";
    }
}
//Ended by Areeba
