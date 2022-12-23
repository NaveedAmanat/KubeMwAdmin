package com.idev4.admin.repository;

import com.idev4.admin.domain.MwDsbltyRpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MwDsbltyRptRepository extends JpaRepository<MwDsbltyRpt, Long> {

    public MwDsbltyRpt findOneByDsbltyRptSeqAndCrntRecFlg(Long dsbltyRptSeq, boolean crntRecFlg);
}
