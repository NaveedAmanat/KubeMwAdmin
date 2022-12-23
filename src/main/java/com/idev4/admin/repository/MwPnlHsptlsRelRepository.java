package com.idev4.admin.repository;

import com.idev4.admin.domain.MwPnlHsptlsRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Authored by Areeba
Dated 14-3-2022
Jubliee Panel Hospital List for KSZB clients
*/

public interface MwPnlHsptlsRelRepository extends JpaRepository<MwPnlHsptlsRel, Long> {

    MwPnlHsptlsRel findByBrnchSeqAndHsptlsIdAndCrntRecFlg(Long brnchSeq, Long hsptlsId, Long flg);

    MwPnlHsptlsRel findByBrnchSeqAndHsptlsId(Long brnchSeq, Long hsptlsId);

    MwPnlHsptlsRel findByIdAndCrntRecFlg(Long id, Long flg);

    List<MwPnlHsptlsRel> findAllByHsptlsIdAndCrntRecFlg(Long id, Long flg);

    //@Override
    List<MwPnlHsptlsRel> findAllByCrntRecFlg(Long flg);
}
//Ended by Areeba