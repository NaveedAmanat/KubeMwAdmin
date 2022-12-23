package com.idev4.admin.repository;

import com.idev4.admin.domain.MwLoanAppDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MwLoanAppDoc entity.
 */
@Repository
public interface MwLoanAppDocRepository extends JpaRepository<MwLoanAppDoc, Long> {

    public MwLoanAppDoc findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(long seq, long dSeq, boolean flag);

    @Query(value = "SELECT DOC.*\n" +
            "  FROM MW_LOAN_APP_DOC DOC\n" +
            " WHERE     DOC.LOAN_APP_SEQ = :P_LOAN_APP_SEQ\n" +
            "       AND DOC.CRNT_REC_FLG = 1\n" +
            "       AND DOC.DOC_SEQ IN (11, 12, 13, 14, 15, 16, 17, 18, 19)", nativeQuery = true)
    public List<MwLoanAppDoc> findByLoanAppDocSeqAndDocSeqIn(@Param("P_LOAN_APP_SEQ") Long loanAppSeq);
}
