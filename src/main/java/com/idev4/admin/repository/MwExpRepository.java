package com.idev4.admin.repository;

import com.idev4.admin.domain.MwExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwExpRepository extends JpaRepository<MwExp, Long> {

    public MwExp findOneByExpSeqAndCrntRecFlg(Long expSeq, boolean flag);

    public List<MwExp> findAllByExpSeq(Long expSeq);

    public List<MwExp> findAllByCrntRecFlgOrderByExpSeqDesc(boolean flag);

    public List<MwExp> findAllByCrntRecFlgAndDelFlgOrCrntRecFlgAndDelFlgOrderByExpSeqDesc(boolean cflag, boolean dflag, boolean ocflag,
                                                                                          boolean odflag);

    public MwExp findOneByExpSeq(Long expSeq);

    public List<MwExp> findAllByBrnchSeqAndCrntRecFlgOrderByExpSeqDesc(Long brnchSeq, boolean flag);

    public List<MwExp> findAllByBrnchSeqAndCrntRecFlgAndDelFlgOrBrnchSeqAndCrntRecFlgAndDelFlgOrderByExpSeqDesc(Long brnchSeq,
                                                                                                                boolean flag, boolean dlfg, Long obrnchSeq, boolean oflag, boolean odlfg);

    @Query(value = "select sum(EXPNS_AMT) from MW_EXP where brnch_seq=:brnchSeq and CRTD_DT between trunc(sysdate) - (to_number(to_char(sysdate,'DD')) - 1) and add_months(trunc(sysdate) - (to_number(to_char(sysdate,'DD')) - 1), 1) -1", nativeQuery = true)
    public Long findSumByCrntMnthAndCrntYear(@Param("brnchSeq") Long brnchSeq);

    @Query(value = "select e.* \r\n" + "from mw_exp e\r\n" + "join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG=1\r\n"
            + "where TYP_ID NOT IN('0424') AND TYP_CTGRY_KEY =2 AND e.BRNCH_SEQ =:brnchSeq ANd e.CRNT_REC_FLG = 1\r\n"
            + "order by 1 desc", nativeQuery = true)
    public List<MwExp> findExpByBranchSeq(@Param("brnchSeq") Long brnchSeq);
    // TYP_ID NOT IN('0006','0343','0424')

    @Query(value = "select exp.* from mw_exp exp\r\n"
            + "join mw_rcvry_trx trx on to_char(trx.RCVRY_TRX_SEQ) = to_char(exp.EXP_REF) and trx.crnt_rec_flg=1\r\n"
            + "join mw_typs typ on typ.typ_seq=EXP.expns_typ_seq and typ.crnt_rec_flg=1 and typ.typ_id='0005'\r\n"
            + "join mw_dth_rpt dth on to_char(dth.clnt_seq) = to_char(trx.pymt_ref) and dth.crnt_rec_flg=1 and dth.dth_rpt_seq=:dthRptSeq\r\n"
            + "where exp.crnt_rec_flg=1 and exp.del_flg=0 and to_char(trx.pymt_ref)=to_char(dth.clnt_seq) and exp.CRTD_DT>dth.CRTD_DT", nativeQuery = true)
    public List<MwExp> findAllExcessExpByDthRptSeq(@Param("dthRptSeq") Long dthRptSeq);

    //Added by Areeba
    @Query(value = " select exp.* from mw_exp exp\n" +
            "            join mw_rcvry_trx trx on to_char(trx.RCVRY_TRX_SEQ) = to_char(exp.EXP_REF) and trx.crnt_rec_flg=1\n" +
            "            join mw_typs typ on typ.typ_seq=EXP.expns_typ_seq and typ.crnt_rec_flg=1 and typ.typ_id='0005'\n" +
            "            join mw_dsblty_rpt dsb on to_char(dsb.clnt_seq) = to_char(trx.pymt_ref) and dsb.crnt_rec_flg=1 and dsb.dsblty_rpt_seq=:dsbltyRptSeq\n" +
            "            where exp.crnt_rec_flg=1 and exp.del_flg=0 and to_char(trx.pymt_ref)=to_char(dsb.clnt_seq) and exp.CRTD_DT>dsb.CRTD_DT", nativeQuery = true)
    public List<MwExp> findAllExcessExpByDsbltyRptSeq(@Param("dsbltyRptSeq") Long dsbltyRptSeq);
    //Ended by Areeba
}
