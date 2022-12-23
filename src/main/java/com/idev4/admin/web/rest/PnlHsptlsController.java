package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwPnlHsptls;
import com.idev4.admin.dto.MwPnlHsptlsDTO;
import com.idev4.admin.service.PnlHsptlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Authored by Areeba
Dated 14-3-2022
Jubliee Panel Hospital List for KSZB clients
*/

@RestController
@RequestMapping("/api")
public class PnlHsptlsController {

    private final Logger log = LoggerFactory.getLogger(PnlHsptlsController.class);
    private final PnlHsptlsService pnlHsptlsService;

    public PnlHsptlsController(PnlHsptlsService pnlHsptlsService) {
        this.pnlHsptlsService = pnlHsptlsService;
    }


    @GetMapping("/get-all-hospitals")
    @Timed
    public ResponseEntity<List<MwPnlHsptls>> getAllHospitals(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnches");
        List<MwPnlHsptls> hospitals = pnlHsptlsService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(hospitals);
    }

    @GetMapping("/get-not-blacklisted-hospitals")
    @Timed
    public ResponseEntity<List<MwPnlHsptls>> getActiveHospitals(Pageable pageable) {
        log.debug("REST request to get a page of MwHospitals");
        List<MwPnlHsptls> hospitals = pnlHsptlsService.findAllNonBlacklistRecord();
        return ResponseEntity.ok().body(hospitals);
    }

    @GetMapping("/get-panel-hospital-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getPanelHospitalList(@RequestHeader Map<String, Object> reqHeader,
                                                                    @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                    @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Panel Hospitals. ");

        Map<String, Object> mwPnlHsptls = pnlHsptlsService.getAllPanelHospitals(pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwPnlHsptls);
    }

    @PostMapping("/add-panel-hospital")
    @Timed
    public ResponseEntity<Map> createPnlHsptl(@RequestBody MwPnlHsptlsDTO dto) throws URISyntaxException {
        log.debug("REST request to save createPnlHsptl : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int result = pnlHsptlsService.save(dto);
        if (result == 0)
            resp.put("exists", "Panel Hospital Already Exists against this Branch");
        else
            resp.put("saved", "Panel Hospital Added Successfully");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/add-hospital")
    @Timed
    public ResponseEntity<Map> createHsptl(@RequestBody MwPnlHsptlsDTO dto) throws URISyntaxException {
        log.debug("REST request to save createHsptl : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int result = pnlHsptlsService.saveHsptl(dto);
        if (result == 1)
            resp.put("saved", "Hospital Added Successfully");
        else
            resp.put("exists", "Hospital Already Exists");

        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/upload-panel-hospital")
    @Timed
    public ResponseEntity<Map> uploadPnlHsptl(@RequestBody List<MwPnlHsptlsDTO> dto) {
        Map<String, String> resp = new HashMap<>();

        resp.put("ListUpload", pnlHsptlsService.makeDataSet(dto));

        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping("/delete-panel-hospital/{seq}")
    @Timed
    public ResponseEntity<Map> deletePnlHsptl(@PathVariable Long seq) {
        log.debug("REST request to delete deletePnlHsptl : {}", seq);
        Map<String, String> resp = new HashMap<String, String>();
        if (pnlHsptlsService.delete(seq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else if (!pnlHsptlsService.delete(seq)) {
            resp.put("error", "Panel Hospital not Found");
            return ResponseEntity.ok().body(resp);
        } else
            return ResponseEntity.badRequest().body(resp);
    }

    @DeleteMapping("/delete-hospital/{seq}")
    @Timed
    public ResponseEntity<Map> deleteHsptl(@PathVariable Long seq) {
        log.debug("REST request to delete deleteHsptl : {}", seq);
        Map<String, String> resp = new HashMap<String, String>();
        if (pnlHsptlsService.deleteHsptl(seq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else if (!pnlHsptlsService.deleteHsptl(seq)) {
            resp.put("error", "Hospital not Found");
            return ResponseEntity.ok().body(resp);
        } else {
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @DeleteMapping("/delete-all-panel-hospitals")
    @Timed
    public ResponseEntity<Map> deleteAllPnlHsptls() {
        Map<String, String> resp = new HashMap<String, String>();
        log.debug("REST request to delete deleteAllPnlHsptls : {}");
        int entries = pnlHsptlsService.deleteAll();
        if (entries > 0) {
            resp.put("data", entries + " entries have been deleted.");
            return ResponseEntity.ok().body(resp);
        } else if (entries == 0) {
            resp.put("data", "There is no data to be deleted.");
            return ResponseEntity.ok().body(resp);
        } else
            return ResponseEntity.badRequest().body(resp);

    }

    @PutMapping("/update-panel-hospital")
    @Timed
    public ResponseEntity<Map> updatePnlHsptl(@RequestBody MwPnlHsptlsDTO dto) throws URISyntaxException {
        log.debug("REST request to update updateRule : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int flag = pnlHsptlsService.update(dto);

        if (flag == -1) {
            resp.put("error", "Panel Hospital Not Found !!");
            return ResponseEntity.ok().body(resp);
            //return ResponseEntity.badRequest().body(resp);
        } else if (flag == 0) {
            resp.put("exits", "Panel Hospital Already Exists");
            return ResponseEntity.ok().body(resp);
        }

        resp.put("update", "Panel Hospital updated Successfully");
        return ResponseEntity.ok().body(resp);
    }
}
//Ended by Areeba