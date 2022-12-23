package com.idev4.admin.repository;

import com.idev4.admin.domain.MwClntTagList;
import org.springframework.data.jpa.repository.JpaRepository;

/* AUTHOR: Areeba
 *   Dated 1-2-2022
 *   Client Tag List
 */
public interface MwClntTagListRepository extends JpaRepository<MwClntTagList, Long> {

    //MwClntTagList findByClntTagListSeq(long seq);
    MwClntTagList findAllByClntTagListSeqAndCrntRecFlg(long seq, long flg);
}
