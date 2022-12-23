package com.idev4.admin.repository;

import com.idev4.admin.domain.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ClientRepository extends JpaRepository<Clients, Long> {

    @Query(value = "select clnt.clnt_id,clnt.clnt_seq,clnt.frst_nm, clnt.last_nm,sum(app.aprvd_loan_amt) loan_amt, sum(rcv.pymt_amt) rcvd_amt\r\n" +
            "from mw_clnt clnt\r\n" +
            "join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1 \r\n" +
            "join mw_ref_cd_val val on val.ref_cd_seq=app.loan_app_sts and val.crnt_rec_flg=1 and val.del_flg=0 and val.ref_cd ='0005'\r\n" +
            "join mw_ref_cd_grp grp on grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.crnt_rec_flg=1 and grp.ref_cd_grp = '0106'\r\n" +
            "join mw_rcvry_trx rcv on rcv.pymt_ref=clnt.clnt_seq and rcv.crnt_rec_flg = 1\r\n" +
            "where clnt.crnt_rec_flg=1 \r\n" +
            "group by clnt.clnt_id,clnt.clnt_seq,clnt.frst_nm, clnt.last_nm", nativeQuery = true)
    public List<Map> findAllClientInfo();


    // Added by Zohaib Asim - Dated 24-08-2021 - CR: Sanction List - Phase 2
    public Clients findAllByCrntRecFlgAndCnicNum(boolean crntRecFlg, Long cnic);
    // End by Zohaib Asim

}
