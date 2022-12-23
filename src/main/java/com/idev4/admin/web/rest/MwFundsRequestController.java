package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.dto.MwFundsRequestDto;
import com.idev4.admin.service.MwFundsRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Added, Naveed
 * @Date, 14-06-2022
 * @Description, SCR - systemization Funds Request
 */

@RestController
@RequestMapping("/api")
public class MwFundsRequestController {

    private final Logger log = LoggerFactory.getLogger(MwFundsRequestController.class);
    @Autowired
    private MwFundsRequestService fundsService;

    @GetMapping("/get-all-branch-funds-request")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllBranchFunds(@RequestHeader Map<String, Object> reqHeader, @RequestParam String toDate,
                                                                 @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                 @RequestParam String filter, @RequestParam Boolean isCount) {
        log.debug("get all Branch Funds Request ");
        Map<String, Object> processes = fundsService.getAllBranchFunds(toDate, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping("/add-branch-funds-request")
    @Timed
    public ResponseEntity<Map<String, Object>> addBranchFundsRequest(@RequestBody MwFundsRequestDto fundsRequest) {

        log.debug("add Branch Funds Request Against " + fundsRequest.brnchNm + " " + fundsRequest.regNm);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> fund = fundsService.addBranchFudsRequest(fundsRequest, user);
        return ResponseEntity.ok().body(fund);
    }

    @GetMapping("/get-branch-funds-request-detail/{acctNum}/{toDate}")
    @Timed
    public ResponseEntity<Map<String, Object>> getFundDetailByAccountNumAndDate(@PathVariable String acctNum, @PathVariable String toDate) {

        Map<String, Object> fund = fundsService.getFundDetailByAccountNumAndDate(acctNum, toDate);

        return ResponseEntity.ok().body(fund);
    }

    @PutMapping("/update-branch-funds-request")
    @Timed
    public ResponseEntity<Map<String, Object>> updateBranchFundsRequest(@RequestBody MwFundsRequestDto fundsRequest) {
        log.debug("update Branch Funds Request Against fundSeq " + fundsRequest.fundSeq);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> fund = fundsService.updateBranchFudsRequest(fundsRequest, user);

        return ResponseEntity.ok().body(fund);
    }
}
