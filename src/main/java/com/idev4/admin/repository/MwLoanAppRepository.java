package com.idev4.admin.repository;

import com.idev4.admin.domain.MwLoanApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwLoanApp entity.
 */
@Repository
public interface MwLoanAppRepository extends JpaRepository<MwLoanApp, Long> {

    public List<MwLoanApp> findAllByLoanAppSeqInAndCrntRecFlg(List<Long> loanAppSeqs, boolean flag);

    //Added by Areeba - 22-04-2022 - OD CHECK
    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlg(long clntSeq, Boolean flg);

    // CR-Donor Tagging
    // get loan by loanAppSeq where Active and untagged
    // Added By Naveed - 20-12-2021
    @Query(value = "select app.* from mw_loan_app app \n" +
            "    where app.LOAN_APP_SEQ = :loanAppSeq and app.CRNT_REC_FLG = 1 and app.loan_app_sts = 703 and nvl(app.dnr_seq,0)=0", nativeQuery = true)
    public MwLoanApp findOneByLoanAppSeq(@Param("loanAppSeq") long loanAppSeq);
    // Ended By Naveed - 20-12-2021

    @Query(value = "select ap.*  from  mw_loan_app ap" + "  where ap.loan_app_seq in :loanAppSeqs and ap.crnt_rec_flg=1 "
            + " and nvl(ap.dnr_seq,0)=0", nativeQuery = true)
    public List<MwLoanApp> findAllLoansForTagging(@Param("loanAppSeqs") List<Long> loanAppSeqs);

}
