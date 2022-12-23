package com.idev4.admin.repository;

import com.idev4.admin.domain.MwPnlHsptls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
Authored by Areeba
Dated 14-3-2022
Jubliee Panel Hospital List for KSZB clients
*/

public interface MwPnlHsptlsRepository extends JpaRepository<MwPnlHsptls, Long> {

    MwPnlHsptls findByIdAndCrntRecFlg(Long id, Long flg);

    MwPnlHsptls findById(Long id);

    @Query(value = "SELECT * FROM MW_PNL_HSPTLS PH " +
            " WHERE PH.CRNT_REC_FLG = 1 ", nativeQuery = true)
    List<MwPnlHsptls> getAllHospitals();

    @Query(value = " SELECT * FROM MW_PNL_HSPTLS PH " +
            " WHERE PH.HSPTLS_STS_SEQ NOT IN ( " +
            " SELECT RCV.REF_CD FROM MW_REF_CD_VAL RCV " +
            " WHERE RCV.REF_CD_DSCR = 'BLACKLIST') " +
            " AND PH.CRNT_REC_FLG = 1 ", nativeQuery = true)
    List<MwPnlHsptls> getAllNBlacklistHospitals();


    @Query(value = "SELECT * FROM MW_PNL_HSPTLS" +
            "WHERE HSPTLS_NM = :hsptlsNm" +
            "AND HSPTLS_ADDR = :hsptlsAddr", nativeQuery = true)
    MwPnlHsptls getHsptlId(String hsptlsNm, String hsptlsAddr);
}
//Ended by Areeba