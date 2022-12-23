package com.idev4.admin.repository;

import com.idev4.admin.domain.RegionWiseOutreach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionWiseOutreachRepository extends JpaRepository<RegionWiseOutreach, Long> {

    @Query(value = " SELECT * FROM REGION_WISE_OUTREACH WHERE " +
            "   TRUNC(OUTREACH_MONTH) = LAST_DAY(TO_DATE(:monthDt, 'dd-mm-rrrr'))\n" +
            "   AND REGION_CD = :regionCd " +
            "   ORDER BY TRANS_DATE DESC " +
            "   FETCH FIRST 1 ROWS ONLY ", nativeQuery = true)
    RegionWiseOutreach findOneByRegionCdAndOutreachMonth(@Param("monthDt") String monthDt, @Param("regionCd") Long regionCd);


}
