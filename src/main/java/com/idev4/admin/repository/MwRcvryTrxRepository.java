package com.idev4.admin.repository;

import com.idev4.admin.domain.MwRcvryTrx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface MwRcvryTrxRepository extends JpaRepository<MwRcvryTrx, Long> {

    @Query(value = "select t.gl_acct_num\r\n" + "from mw_rcvry_trx rt\r\n"
            + "join mw_typs t on t.typ_seq=rt.rcvry_typ_seq and t.crnt_rec_flg =1\r\n"
            + "where rt.rcvry_trx_seq =? and rt.crnt_rec_flg =1", nativeQuery = true)
    public Map<String, ?> getGlAcctNumByRcvryTrxSeq(long rcvryTrxSeq);

    public MwRcvryTrx findOneByRcvryTrxSeqAndCrntRecFlg(long rcvryTrxSeq, boolean flag);

    public MwRcvryTrx findAllByRcvryTrxSeqAndPymtRefAndCrntRecFlgOrderByRcvryTrxSeq(long rcvryTrxSeq, Long pymtRef, boolean flag);

    public MwRcvryTrx findOneByInstrNumAndCrntRecFlg(String rcvryTrxSeq, boolean flag);

    public MwRcvryTrx findOneByRcvryTypSeqAndPymtRefAndCrntRecFlg(long rcvryTypSeq, long pymtRef, boolean flag);

    @Query(value = "select rt.* from mw_rcvry_trx rt \r\n" + "where rt.rcvry_trx_seq =? and rt.crnt_rec_flg =0\r\n"
            + "ORDER BY rt.eff_start_dt DESC \r\n" + "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    public MwRcvryTrx findTop1ByRcvryTrxSeqAndCrntRecFlgFalse(long rcvryTrxSeq);

    public MwRcvryTrx findOneByPrntRcvryRefAndCrntRecFlg(long prntRcvryRef, boolean flag);
}
