package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwDsbltyRpt;
import com.idev4.admin.domain.MwDthRpt;
import com.idev4.admin.dto.CollectUpFrontCashDto;
import com.idev4.admin.dto.MwDsbltyRptDTO;
import com.idev4.admin.dto.ReportDeathDTO;
import com.idev4.admin.service.LoanServiceingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoanServiceingController {

    @Autowired
    LoanServiceingService loanServiceingService;

    @PostMapping("/report-death")
    @Timed
    public ResponseEntity<Map> allActiveClnts(@RequestBody ReportDeathDTO dr, @RequestHeader(value = "Authorization") String token) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dr.clntSeq == null || dr.clntSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.deathDt == null || dr.deathDt.isEmpty()) {
            resp.put("error", "Death Date is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (loanServiceingService.incdntDateCheck(dr.clntSeq, dr.deathDt) > 0) {
            resp.put("error", "Death date should be greater then Disbursement date !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.gender == null) {
            resp.put("error", "Gender is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.dethCase == null || dr.dethCase.isEmpty()) {
            resp.put("error", "Death Cause is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.deathcertf == null || dr.deathcertf.isEmpty()) {
            resp.put("error", "Death Certificate is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (loanServiceingService.getClntODOrUnpostedRecvry(dr.clntSeq, dr.deathDt) > 0) {
            resp.put("error", "Client has OD Amount or Unposted Recovery !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // Added by Zohaib Asim - Dated 25-08-2021 CR: Sanction List - Phase 2
        // Death Claim Restriction; if found in Tag List
        if (loanServiceingService.findClntTagged(dr.clntSeq, 0L) == true) {
            resp.put("error", "NACTA Matched. Client and other individual/s (Nominee/CO borrower/Next of Kin) cannot be disbursed.");
            return ResponseEntity.badRequest().body(resp);
        }
        // End by Zohaib Asim

        MwDthRpt exp = loanServiceingService.addMwDthRpt(dr, currUser, token);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    @GetMapping("/revert-report-death")
    public ResponseEntity<Map> revertReportDeath(@RequestParam(required = false) Long dthRptSeq,
                                                 @RequestParam(required = false) String cmnt, @RequestHeader(value = "Authorization") String token) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dthRptSeq == null || dthRptSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        //Modified by Areeba
        boolean expPosted = loanServiceingService.isExcessRecoveryPaid(dthRptSeq, 0);
        if (expPosted) {
            resp.put("error", "Excess Recovery Posted, Reverse Excess Recovery to proceed.");
            return ResponseEntity.badRequest().body(resp);
        }

        Long exp = loanServiceingService.reverseMwDthRpt(dthRptSeq, cmnt, currUser, token);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    @GetMapping("/get-clnt-od-days/{clntSeq}")
    public ResponseEntity<Map> revertReportDeath(@PathVariable Long clntSeq) {
        Map<String, Object> resp = new HashMap<String, Object>();
        if (clntSeq == null || clntSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());

        resp.put("days", loanServiceingService.getClntODOrUnpostedRecvry(clntSeq, date));
        return ResponseEntity.ok().body(resp);

    }

    //Added by Areeba
    @PostMapping("/report-disability")
    @Timed
    public ResponseEntity<Map> allActiveClnts(@RequestBody MwDsbltyRptDTO dr, @RequestHeader(value = "Authorization") String token) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dr.clntSeq == null || dr.clntSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.dtOfDsblty == null || dr.dtOfDsblty.isEmpty()) {
            resp.put("error", "Disability Date is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.gender == null) {
            resp.put("error", "Client/ Nominee is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (loanServiceingService.incdntDateCheck(dr.clntSeq, dr.dtOfDsblty) > 0) {
            resp.put("error", "Disability date should be greater then Disbursement date !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (loanServiceingService.getClntODOrUnpostedRecvry(dr.clntSeq, dr.dtOfDsblty) > 0) {
            resp.put("error", "Client has OD Amount or Unposted Recovery !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (loanServiceingService.findClntTagged(dr.clntSeq, 0L) == true) {
            resp.put("error", "NACTA Matched. Client and other individual/s (Nominee/CO borrower/Next of Kin) cannot be disbursed.");
            return ResponseEntity.badRequest().body(resp);
        }

        MwDsbltyRpt exp = loanServiceingService.addMwDsbltyRpt(dr, currUser, token);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    @GetMapping("/revert-report-disability")
    public ResponseEntity<Map> revertReportDisability(@RequestParam(required = false) Long dsbltyRptSeq,
                                                      @RequestParam(required = false) String cmnt, @RequestHeader(value = "Authorization") String token) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dsbltyRptSeq == null || dsbltyRptSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        boolean expPosted = loanServiceingService.isExcessRecoveryPaid(dsbltyRptSeq, 1);
        if (expPosted) {
            resp.put("error", "Excess Recovery Posted, Reverse Excess Recovery to proceed.");
            return ResponseEntity.badRequest().body(resp);
        }

        Long exp = loanServiceingService.reverseMwDsbltyRpt(dsbltyRptSeq, cmnt, currUser, token);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    // Zohaib Asim - Dated 29-09-2022 - KSWK - UpFront Cash Collection
    @PostMapping("/collect-upfront-cash")
    @Timed
    public ResponseEntity<Map> collectUpFrontCash(@RequestHeader(value = "Authorization") String token,
                                                  @RequestBody CollectUpFrontCashDto collectUpFrontCashDto) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (collectUpFrontCashDto.getClntId() <= 0) {
            resp.put("error", "Incorrect Client Id!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (collectUpFrontCashDto.getRcvblAmt() <= 0) {
            resp.put("error", "Up-Front Cash Collection should be greater than Zero!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (collectUpFrontCashDto.getAmt() > 0) {
            resp.put("error", "Death Amount should not be greater than Zero!");
            return ResponseEntity.badRequest().body(resp);
        }

        String respStr = loanServiceingService.collectUpFrontCashBfDeathPost(collectUpFrontCashDto, currUser);
        if (respStr.equals("success")) {
            resp.put("success", "Cash Received");
        } else {
            resp.put("error", "Unable to Post Cash");
        }

        return ResponseEntity.ok().body(resp);
    }

//    @PutMapping("/update-disability/{dsbltyRptSeq}/{rcvryTypsStr}")
//    @Timed
//    public ResponseEntity<Map> updateMwDsbltyRpt(@PathVariable Long dsbltyRptSeq,
//                                              @PathVariable String rcvryTypsStr) throws URISyntaxException {
//        Map<String, String> resp = new HashMap<String, String>();
//        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        int flag = loanServiceingService.updateMwDsbltyRpt(dsbltyRptSeq, rcvryTypsStr, currUser);
//
//        if (flag == -1) {
//            resp.put("error", "Disability Not Found !!");
//            return ResponseEntity.badRequest().body(resp);
//        }
//
//        resp.put("update", "Receivable Collected Successfully");
//        return ResponseEntity.ok().body(resp);
//    }
    //Ended by Areeba

}
