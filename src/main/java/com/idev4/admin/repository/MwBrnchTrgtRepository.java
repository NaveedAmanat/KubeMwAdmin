package com.idev4.admin.repository;

import com.idev4.admin.domain.MwBrnchTrgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MwBrnchTrgtRepository extends JpaRepository<MwBrnchTrgt, Long> {

    MwBrnchTrgt findOneByBrnchTargetsSeqAndDelFlg(Long brnchTargetsSeq, Boolean delFlg);
}
