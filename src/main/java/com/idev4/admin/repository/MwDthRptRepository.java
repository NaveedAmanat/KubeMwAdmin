package com.idev4.admin.repository;

import com.idev4.admin.domain.MwDthRpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MwDthRptRepository extends JpaRepository<MwDthRpt, Long> {

    public MwDthRpt findOneByDthRptSeqAndCrntRecFlg(Long dthRptSeq, boolean crntRecFlg);

    public List<MwDthRpt> findAllByClntSeqAndClntNomFlgAndCrntRecFlg(Long ClntSeq, Integer nmflg, boolean crntRecFlg);

    public MwDthRpt findOneByClntSeqAndCrntRecFlg(Long dthRptSeq, boolean crntRecFlg);

    //public List<MwDthRpt> findAllByClntSeqAndClntNomFlgAndDthCertNumAAndCrntRecFlg(Long clntSeq, Long gender, String  deathcertf, boolean crntRecFlg);

    @Query(value = "SELECT RPT.*\n" +
            "  FROM MW_DTH_RPT RPT\n" +
            " WHERE     RPT.CLNT_SEQ = :P_CLNT_SEQ\n" +
            "       AND RPT.CLNT_NOM_FLG = :CLNT_NOM_FLG\n" +
            "       AND RPT.DTH_CERT_NUM = :DTH_CERT_NUM\n" +
            "       AND RPT.CRNT_REC_FLG = 1", nativeQuery = true)
    public List<MwDthRpt> findByClntSeqAndCrntRecFlgAndDtOfDth(@Param("P_CLNT_SEQ") Long clntSeq, @Param("CLNT_NOM_FLG") long gender, @Param("DTH_CERT_NUM") String deathcertf);
}
