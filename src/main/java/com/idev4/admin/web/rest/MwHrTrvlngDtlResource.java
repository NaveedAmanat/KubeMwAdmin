package com.idev4.admin.web.rest;

/*Authored by Areeba
HR Travelling SCR
Dated - 23-06-2022
*/

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwHrTrvlngDtl;
import com.idev4.admin.service.MwHrTrvlngDtlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MwHrTrvlngDtlResource {

    private final Logger log = LoggerFactory.getLogger(MwHrTrvlngDtlResource.class);

    private final MwHrTrvlngDtlService mwHrTrvlngDtlService;

    public MwHrTrvlngDtlResource(MwHrTrvlngDtlService mwHrTrvlngDtlService) {
        this.mwHrTrvlngDtlService = mwHrTrvlngDtlService;
    }

    @GetMapping("/get-trvlng-dtl")
    public List<MwHrTrvlngDtl> getTrvlngDtl() {
        log.debug("getAllTrvlng");
        return mwHrTrvlngDtlService.getTrvlngDtl();
    }

    @GetMapping("/get-trvlng-dtl-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getTrvlngDtlList(@RequestHeader Map<String, Object> reqHeader,
                                                                @RequestParam String monthDt,
                                                                @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Travelling Details of " + monthDt);

        Map<String, Object> mwtrvlngDtl = mwHrTrvlngDtlService.getAllTrvlngDtls(monthDt, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwtrvlngDtl);
    }

    @GetMapping("/run-trvlng-calc")
    public ResponseEntity<Map<String, Object>> callTrvlngCalc(@RequestParam String monthDt) {
        log.info("REST request to callTrvlngCalc ");
        Map<String, Object> mapResp = mwHrTrvlngDtlService.callTrvlngCalc(monthDt);

        return ResponseEntity.ok().body(mapResp);
    }

}
