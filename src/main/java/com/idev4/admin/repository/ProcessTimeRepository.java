package com.idev4.admin.repository;

import com.idev4.admin.domain.ProcessTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTimeRepository extends JpaRepository<ProcessTime, Long> {

    @Query(value = "SELECT * FROM KASHF_REPORTING.PROCESS_TIME ", nativeQuery = true)
    List<ProcessTime> getProcesses();
}
