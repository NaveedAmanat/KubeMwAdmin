package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwAnmlRgstr;
import com.idev4.admin.dto.AnmlDeathReportDto;
import com.idev4.admin.dto.AnmlListingDto;
import com.idev4.admin.repository.MwAnmlRgstrRepository;
import com.idev4.admin.service.LoanServiceingService;
import com.idev4.admin.service.MwAnmlRgstrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnimalController {

    @Autowired
    MwAnmlRgstrService mwAnmlRgstrService;

    @Autowired
    LoanServiceingService loanServiceingService;

    @Autowired
    MwAnmlRgstrRepository mwAnmlRgstrRepository;

    @GetMapping("/clnts-anml-list/{clntSeq}")
    @Timed
    public ResponseEntity<List<AnmlListingDto>> allActiveClnts(@PathVariable Long clntSeq) {
        return new ResponseEntity<>(mwAnmlRgstrService.getAllAnml(clntSeq), HttpStatus.OK);
    }

    @PostMapping("/report-anml-death")
    @Timed
    public ResponseEntity<Map> allActiveClnts(@RequestBody AnmlDeathReportDto dr) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dr.anmlRgstrSeq == null || dr.anmlRgstrSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.dthDt == null || dr.dthDt.isEmpty()) {
            resp.put("error", "Death Date is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.dthCase == null || dr.dthCase.isEmpty()) {
            resp.put("error", "Death Cause is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dr.type == null || (dr.type != 3 && dr.type != 4 && dr.type != 5)) {
            resp.put("error", "Type is missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // Added by Zohaib Asim - Dated 25-08-2021 CR: Sanction List - Phase 2
        // Death Claim Restriction; if found in Tag List
        MwAnmlRgstr rgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(dr.anmlRgstrSeq, true);

        if (rgstr != null) {
            if (loanServiceingService.findClntTagged(0L, rgstr.getLoanAppSeq()) == true) {
                resp.put("error", "NACTA Matched. Client and other individual/s (Nominee/CO borrower/Next of Kin) cannot be disbursed.");
                return ResponseEntity.badRequest().body(resp);
            }
        }
        // End by Zohaib Asim

        MwAnmlRgstr exp = mwAnmlRgstrService.addMwDthRpt(dr, currUser);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    @GetMapping("/reverse-anml-death")
    @Timed
    public ResponseEntity<Map> reverseAnmlDeath(@RequestParam(required = false) Long anmlRgstrSeq,
                                                @RequestParam(required = false) String cmnt) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (anmlRgstrSeq == null || anmlRgstrSeq <= 0) {
            resp.put("error", "Seems Incorrect Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        boolean expPosted = loanServiceingService.isRcvryTrxForAnmlRgstrSeq(anmlRgstrSeq);
        if (expPosted) {
            resp.put("error", "Loan Adjusted against this Animal.");
            return ResponseEntity.badRequest().body(resp);
        }
        MwAnmlRgstr exp = mwAnmlRgstrService.revertAnmlDeath(anmlRgstrSeq, currUser);
        resp.put("expSeq", exp);
        return ResponseEntity.ok().body(resp);

    }

    @GetMapping("/anml-dth-rpt")
    public ResponseEntity<List> getAnmlDeathRpt() {
        return ResponseEntity.ok()
                .body(mwAnmlRgstrService.anmlInsrClm(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/mark-dth-rpt-sts/{dthRptSeq}/{stskey}")
    public ResponseEntity<?> markDthRptStatus(@PathVariable Long dthRptSeq, @PathVariable Long stskey, @PathVariable Long amt,
                                              @RequestHeader(value = "Authorization") String token) {

        MwAnmlRgstr rpt = mwAnmlRgstrService.markAnmlInsrSts(dthRptSeq, stskey,
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (rpt == null) {
            return ResponseEntity.badRequest().body("{\"error\":\"MwDthRpt Not Found\"}");
        }
        return ResponseEntity.ok().body(rpt);
    }

    @GetMapping("/mark-dth-rpt-adj/{dthRptSeq}/{stskey}/{amt}")
    public ResponseEntity<?> markDthRptAdj(@PathVariable Long dthRptSeq, @PathVariable Long stskey, @PathVariable Long amt,
                                           @RequestHeader(value = "Authorization") String token) {
        MwAnmlRgstr rpt = mwAnmlRgstrService.markAnmlInsrAdj(dthRptSeq, stskey,
                SecurityContextHolder.getContext().getAuthentication().getName(), amt, token);
        if (rpt == null) {
            return ResponseEntity.badRequest().body("{\"error\":\"MwDthRpt Not Found\"}");
        }
        return ResponseEntity.ok().body(rpt);
    }

    @GetMapping("/mark-dth-rpt-sts-rvrt/{anmlRgstSeq}")
    public ResponseEntity<?> markDthRptStatus(@PathVariable Long anmlRgstSeq) {

        MwAnmlRgstr rpt = mwAnmlRgstrService.markAnmlInsrStsRvrt(anmlRgstSeq,
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (rpt == null) {
            return ResponseEntity.badRequest().body("{\"error\":\"MwDthRpt Not Found\"}");
        }
        return ResponseEntity.ok().body(rpt);
    }

    //Added by Areeba 16-9-2022
    @GetMapping("/check-loan-adj/{seq}/{amount}")
    public ResponseEntity<?> checkLoanAdjAmt(@PathVariable Long seq, @PathVariable Long amount) {

        Boolean rpt = mwAnmlRgstrService.checkAnmlAdj(seq, amount);
        if (rpt == null) {
            return ResponseEntity.badRequest().body("{\"error\":\"Could not check Loan Adjustment Amount\"}");
        }
        return ResponseEntity.ok().body(rpt);
    }
    //Ended by Areeba

}
