package com.idev4.admin.repository;


/*Authored by Areeba
HR Travelling SCR
Dated - 23-06-2022
*/

import com.idev4.admin.domain.MwHrTrvlngDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MwHrTrvlngDtlRepository extends JpaRepository<MwHrTrvlngDtl, Long> {

    List<MwHrTrvlngDtl> findAll();
}
