package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.dto.DonnerUploaderDto;
import com.idev4.admin.dto.DonorClientTaggingDTO;
import com.idev4.admin.dto.DonorTaggingForm;
import com.idev4.admin.service.DonorTaggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DonorTaggingController {

    @Autowired
    DonorTaggingService donorTaggingService;

    @PostMapping("/filter-donor-list")
    @Timed
    public ResponseEntity getFilteredDonors(@RequestBody DonorTaggingForm form) {
        return ResponseEntity.ok().body(donorTaggingService.getDonorTaggingFiltterdList(form));
    }


    @PostMapping("/tag-clients")
    public ResponseEntity allActiveClnts(@RequestBody DonorClientTaggingDTO dto) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.loanAppSeq == null || dto.loanAppSeq.isEmpty()) {
            resp.put("error", "Incorrect Loan App Ids !");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.dnrSeq <= 0) {
            resp.put("error", "Incorrect Donor Seq !");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.fundAmt == null || dto.fundAmt <= 0) {
            resp.put("error", "Incorrect Fund Amount !");
            return ResponseEntity.badRequest().body(resp);
        }
        return ResponseEntity.ok().body(donorTaggingService.TagDonorClients(dto));
    }

    // CR-Donor Tagging
    // tagged clients uploader
    // Added By Naveed - 20-12-2021
    @PostMapping("/tag-clients-uploader")
    public ResponseEntity allActiveClientsUploader(@RequestBody List<DonnerUploaderDto> loanList) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return donorTaggingService.TagDonorClientsUploader(loanList, currUser);
    }
    // Ended By Naveed - 20-12-2021

    // CR-Donor Tagging
    // fetch all District and Branches
    // Added By Naveed - 20-12-2021
    @GetMapping("/dist-branch-detail")
    public ResponseEntity getDistBranchMapped() {
        return donorTaggingService.getDistBranchDetail();
    }
    // Ended By Naveed - 20-12-2021
}