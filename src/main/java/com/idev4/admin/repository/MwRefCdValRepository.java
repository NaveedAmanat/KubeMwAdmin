package com.idev4.admin.repository;

import com.idev4.admin.domain.MwRefCdVal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwRefCdVal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwRefCdValRepository extends JpaRepository<MwRefCdVal, Long> {

    @Query(value = "select val.ref_cd_seq, val.eff_start_dt, val.ref_cd_grp_key, val.ref_cd, val.ref_cd_dscr, val.ref_cd_sort_ordr, val.ref_cd_active_flg, val.crtd_by, val.crtd_dt, val.last_upd_by, val.last_upd_dt, val.del_flg, val.eff_end_dt, val.crnt_rec_flg\n"
            + "from mw_ref_cd_val val\n"
            + "join mw_ref_cd_grp grp on grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.CRNT_REC_FLG=1 and grp.ref_cd_grp = :refCdGrp\n"
            + "where val.CRNT_REC_FLG=1 and val.ref_cd =:refCd", nativeQuery = true)
    public MwRefCdVal findRefCdByGrpAndVal(@Param("refCdGrp") String refCdGrp, @Param("refCd") String refCd);

}
