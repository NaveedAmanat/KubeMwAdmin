package com.idev4.admin.repository;

import com.idev4.admin.domain.MwVehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MwVehicleInfoRepository extends JpaRepository<MwVehicleInfo, Long> {

    MwVehicleInfo findOneByLoanAppSeqAndCrntRecFlg(long loapAppSeq, boolean flag);
}
