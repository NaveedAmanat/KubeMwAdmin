package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.dto.MwBrnchTrgtDTO;
import com.idev4.admin.dto.RegionWiseOutreachDTO;
import com.idev4.admin.service.TargetOutreachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TargetOutreachController {

    private final Logger log = LoggerFactory.getLogger(TargetOutreachController.class);

    @Autowired
    TargetOutreachService targetOutreachService;

    @GetMapping("/get-mw-brnch-trgt")
    @Timed
    public ResponseEntity<Map<String, Object>> getMwBrnchTrgt(@RequestHeader Map<String, Object> reqHeader,
                                                              @RequestParam String monthDt,
                                                              @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                              @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to getMwBrnchTrgt of " + monthDt);

        Map<String, Object> mwBrnchTrgt = targetOutreachService.getMwBrnchTrgt(monthDt, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwBrnchTrgt);
    }

    @GetMapping("/get-region-wise-outreach")
    @Timed
    public ResponseEntity<Map<String, Object>> getRegionWiseOutreach(@RequestHeader Map<String, Object> reqHeader,
                                                                     @RequestParam String monthDt,
                                                                     @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                     @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to getRegionWiseOutreach of " + monthDt);

        Map<String, Object> regionWiseOutreach = targetOutreachService.getRegionWiseOutreach(monthDt, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(regionWiseOutreach);
    }

//    @GetMapping("/get-trvlng/{trvlngRol}")
//    public List<MwHrTrvlngDTO> getTrvlngByPortfolio(@PathVariable Long trvlngRol) {
//        log.debug("getTrvlngByPortfolio");
//        return mwHrTrvlngService.getTrvlng(trvlngRol);
//    }

    @PostMapping("/add-mw-brnch-trgt")
    @Timed
    public ResponseEntity<Map> addMwBrnchTrgt(@RequestBody MwBrnchTrgtDTO mwBrnchTrgtDTO) throws URISyntaxException {
        log.debug("REST request to save mwBrnchTrgt : {}", mwBrnchTrgtDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = targetOutreachService.addMwBrnchTrgt(mwBrnchTrgtDTO, currUser);

        if (info == 1) {
            respData.put("trgt", "Successfully added.");
        } else if (info == 0) {
            respData.put("trgt", "Could not be added.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PostMapping("/add-region-wise-outreach")
    @Timed
    public ResponseEntity<Map> addRegionWiseOutreach(@RequestBody RegionWiseOutreachDTO regionWiseOutreachDTO) throws URISyntaxException, ParseException {
        log.debug("REST request to save RegionWiseOutreach : {}", regionWiseOutreachDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = targetOutreachService.addRegionWiseOutreach(regionWiseOutreachDTO, currUser);

        if (info == 1) {
            respData.put("outreach", "Successfully added.");
        } else if (info == 0) {
            respData.put("outreach", "Could not be added.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/delete-mw-brnch-trgt/{brnchTargetsSeq}")
    @Timed
    public ResponseEntity<Map> deleteMwBrnchTrgt(@PathVariable Long brnchTargetsSeq) throws URISyntaxException {
        log.debug("REST request to delete MwBrnchTrgt : {}", brnchTargetsSeq);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = targetOutreachService.deleteMwBrnchTrgt(brnchTargetsSeq, currUser);

        if (info == 1) {
            respData.put("trgt", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("trgt", "Could not be deleted.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/update-mw-brnch-trgt")
    @Timed
    public ResponseEntity<Map> updateMwBrnchTrgt(@RequestBody MwBrnchTrgtDTO mwBrnchTrgtDTO) throws URISyntaxException {
        log.debug("REST request to update MwBrnchTrgt : {}", mwBrnchTrgtDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = targetOutreachService.updateMwBrnchTrgt(mwBrnchTrgtDTO, currUser);

        if (info == 1) {
            respData.put("trgt", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("trgt", "Could not be deleted.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/update-region-wise-outreach")
    @Timed
    public ResponseEntity<Map> updateRegionWiseOutreach(@RequestBody RegionWiseOutreachDTO regionWiseOutreachDTO) throws URISyntaxException, ParseException {
        log.debug("REST request to update RegionWiseOutreach : {}", regionWiseOutreachDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = targetOutreachService.updateRegionWiseOutreach(regionWiseOutreachDTO, currUser);

        if (info == 1) {
            respData.put("outreach", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("outreach", "Could not be deleted.");
        }
        return ResponseEntity.ok().body(respData);

    }
}
