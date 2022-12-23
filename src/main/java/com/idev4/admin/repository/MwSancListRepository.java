package com.idev4.admin.repository;

import com.idev4.admin.domain.MwSancList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MwSancListRepository extends JpaRepository<MwSancList, Long> {

    MwSancList findBySancSeq(long seq);

    List<MwSancList> findAllBySancType(String sancType);

    List<MwSancList> findAllByCnicNumAndCrntRecFlg(String cnicNum, Long crntRecFlg);

    List<MwSancList> findAllByCrntRecFlgAndIsValidCnicAndCntry(Long crntRecFlg, Long isValidCnic, String country);

    @Query(value = "SELECT * FROM MW_SANC_LIST SL " +
            " WHERE SL.CRNT_REC_FLG = :crntRecFlg AND SL.IS_VALID_CNIC = :isValidCnic" +
            " AND SL.CNTRY = :country AND SL.SANC_TYPE LIKE :sancType", nativeQuery = true)
    List<MwSancList> findAllByCrntRecFlgAndIsValidCnicAndCntryAndSancTypeLike(@Param("crntRecFlg") Long crntRecFlg,
                                                                              @Param("isValidCnic") Long isValidCnic,
                                                                              @Param("country") String country,
                                                                              @Param("sancType") String sancType);

    List<MwSancList> findAllByCrntRecFlgAndIsMtchFoundAndSancTypeLike(Long crntRecFlg, Long isMtchFound, String fileType);

    List<MwSancList> findAllByCnicNumAndFrstNmAndLastNmAndFatherNmAndDstrctAndPrvnceAndCntry(String cnicNo, String frstNm,
                                                                                             String lastNm, String FatherNm,
                                                                                             String dstrct, String prvnce,
                                                                                             String cntry);

}
