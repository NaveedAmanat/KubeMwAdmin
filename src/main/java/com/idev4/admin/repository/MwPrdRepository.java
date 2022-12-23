package com.idev4.admin.repository;

import com.idev4.admin.domain.MwPrd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwLoanApp entity.
 */
@Repository
public interface MwPrdRepository extends JpaRepository<MwPrd, Long> {

    @Query(value = " SELECT PRD.* FROM MW_LOAN_APP LA \n" +
            "                 JOIN MW_PRD PRD ON PRD.PRD_SEQ = LA.PRD_SEQ AND PRD.CRNT_REC_FLG = 1 \n" +
            "                 WHERE LA.CLNT_SEQ = :clnt_seq AND LA.CRNT_REC_FLG = 1 ", nativeQuery = true)
    public List<MwPrd> getAllPrdGrpByLoanAppSeq(@Param("clnt_seq") long clntSeq);
}

