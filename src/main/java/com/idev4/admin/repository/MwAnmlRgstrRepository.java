package com.idev4.admin.repository;

import com.idev4.admin.domain.MwAnmlRgstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MwAnmlRgstrRepository extends JpaRepository<MwAnmlRgstr, Long> {

    @Query(value = "select ar.ANML_RGSTR_SEQ, ar.EFF_START_DT, ar.LOAN_APP_SEQ, ar.RGSTR_CD, ar.TAG_NUM, ar.ANML_KND, ar.ANML_TYP, ar.ANML_CLR, ar.ANML_BRD, ar.PRCH_DT, ar.AGE_YR, ar.AGE_MNTH, ar.PRCH_AMT, ar.PIC_DT, ar.ANML_PIC, ar.TAG_PIC, ar.CRTD_BY, ar.CRTD_DT, ar.LAST_UPD_BY, ar.LAST_UPD_DT, ar.DEL_FLG, ar.EFF_END_DT, ar.CRNT_REC_FLG\r\n"
            + "from mw_anml_rgstr ar\r\n" + "join mw_loan_app la on la.LOAN_APP_SEQ = ar.LOAN_APP_SEQ and la.CRNT_REC_FLG = 1\r\n"
            + "where ar.CRNT_REC_FLG = 1 and la.CLNT_SEQ=?", nativeQuery = true)
    public List<MwAnmlRgstr> findAllByClntSeq(long clntSeq);

    public MwAnmlRgstr findOneByAnmlRgstrSeqAndCrntRecFlg(long anmlRgstrSeq, boolean flg);

    //Added by Areeba
    @Query(value = " SELECT LOAN_APP_SEQ \n" +
            " FROM MW_LOAN_APP MLA\n" +
            " JOIN MW_PRD MP ON MLA.PRD_SEQ = MP.PRD_SEQ AND MP.CRNT_REC_FLG = 1 AND MP.PRD_STS_KEY = 200\n" +
            " JOIN MW_PRD_GRP MPG ON MP.PRD_GRP_SEQ = MPG.PRD_GRP_SEQ AND MPG.PRD_GRP_ID = '0016' AND MPG.PRD_GRP_STS = 200 AND MPG.CRNT_REC_FLG = 1\n" +
            " WHERE MLA.CLNT_SEQ = :clntSeq AND MLA.LOAN_APP_STS = 703 AND MLA.CRNT_REC_FLG = 1 ", nativeQuery = true)
    public Long findAnmlLoanAppByClntSeq(@Param("clntSeq") Long clntSeq);
    //Ended by Areeba
}
