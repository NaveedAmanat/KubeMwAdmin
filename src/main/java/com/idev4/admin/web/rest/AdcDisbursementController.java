package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.service.AdcDisbursementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Added by Naveed - Date - 10-05-2022
 * SCR - MCB Disbursement
 */

@RestController
@RequestMapping("/api")
public class AdcDisbursementController {


    private final Logger log = LoggerFactory.getLogger(AdcDisbursementController.class);

    @Autowired
    private AdcDisbursementService service;

    @GetMapping("/get-all")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllAdcDisbursement(@RequestHeader Map<String, Object> reqHeader, @RequestParam Long branchSeq,
                                                                     @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                     @RequestParam String filter, @RequestParam Boolean isCount) {
        log.debug("get all ADC disbursement ");
        Map<String, Object> processes = service.getAllByBranch(branchSeq, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(processes);
    }

    @GetMapping("/upd-dsbmt-rvrsl-reason")
    public ResponseEntity<Map<String, Object>> updDsbmtRvrslReason(@RequestParam Long dsbmtDtlKey, @RequestParam String remarks) {
        log.debug("Update disbursement reversal reasons ");
        Map<String, Object> dsbmtRvrsl = service.updDsbmtRvrslReason(dsbmtDtlKey, remarks);
        return ResponseEntity.ok().body(dsbmtRvrsl);
    }

    @GetMapping("/discard-dsbmt-rvrsl-reason")
    public ResponseEntity<Map<String, Object>> discardDsbmtRvrslReason(@RequestParam Long dsbmtDtlKey) {
        log.debug("Discard disbursement reversal reasons ");
        Map<String, Object> dsbmtRvrsl = service.discardDsbmtRvrslReason(dsbmtDtlKey);
        return ResponseEntity.ok().body(dsbmtRvrsl);
    }
}
