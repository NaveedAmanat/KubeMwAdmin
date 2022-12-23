package com.idev4.admin.repository;

import com.idev4.admin.domain.MwDnrTgng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwDnrTgngRepository extends JpaRepository<MwDnrTgng, Long> {


    // CR-Donor Tagging
    // Fetch all district for Filter (District and Branch)
    // Added By Naveed - 20-12-2021
    @Query(value = "select distinct ds.DIST_SEQ, ds.DIST_NM, mb.BRNCH_SEQ, mb.brnch_nm\n" +
            "from mw_dist ds\n" +
            "join mw_thsl mt on mt.DIST_SEQ = ds.DIST_SEQ and mt.CRNT_REC_FLG = 1\n" +
            "join mw_uc uc on uc.THSL_SEQ = mt.THSL_SEQ and uc.CRNT_REC_FLG = 1\n" +
            "join mw_city_uc_rel url on url.UC_SEQ = uc.UC_SEQ and url.CRNT_REC_FLG = 1\n" +
            "join mw_brnch_location_rel rl on rl.CITY_SEQ = url.CITY_UC_REL_SEQ and rl.CRNT_REC_FLG = 1\n" +
            "join mw_brnch mb on mb.BRNCH_SEQ = rl.BRNCH_SEQ and mb.CRNT_REC_FLG = 1\n" +
            "where ds.CRNT_REC_FLG = 1 and ds.DIST_SEQ <> -1\n" +
            "order by 1", nativeQuery = true)
    public List<Object[]> findAllDistrictByBranch();
    // Ended By Naveed - 20-12-2021
}
