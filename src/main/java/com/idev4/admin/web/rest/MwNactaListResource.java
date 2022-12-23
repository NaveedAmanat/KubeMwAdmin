package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwSancList;
import com.idev4.admin.service.MwNactaListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MwNactaListResource {

    private final Logger log = LoggerFactory.getLogger(MwNactaListResource.class);

    private final MwNactaListService mwNactaListService;

    public MwNactaListResource(MwNactaListService mwNactaListService) {
        this.mwNactaListService = mwNactaListService;
    }

    /*
     * Modified by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
     * Parameter added File Type
     * */
    @GetMapping("/get-nacta-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getRul(@RequestParam String fileType,
                                                      @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                      @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Nacta/Sanction list : {}");
        //Map<String, Object> mwRul = mwNactaListService.findAllNactaList(pageIndex, pageSize, filter, isCount);
        Map<String, Object> mwRul = mwNactaListService.findAllSancList(fileType, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRul);
    }

    @PostMapping("/add-nacta")
    @Timed
    public ResponseEntity<Map> createNacta(@RequestBody MwSancList dto) throws URISyntaxException {
        log.debug("REST request to save createNacta : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwNactaListService.isClientExits(dto.getCnicNum())) {
            resp.put("exits", "Client Already Exists against this Cnic");
            return ResponseEntity.ok().body(resp);
        }

        MwSancList list = mwNactaListService.save(dto);
        resp.put("saved", "Client Added Successfully");
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-nacta")
    @Timed
    public ResponseEntity<Map> updateRule(@RequestBody MwSancList dto) throws URISyntaxException {
        log.debug("REST request to update updateRule : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int flag = mwNactaListService.update(dto);

        if (flag == -1) {
            resp.put("error", "Nacta Client Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        } else if (flag == 0) {
            resp.put("exits", "Client Already Exists against this Cnic");
            return ResponseEntity.ok().body(resp);
        }

        resp.put("update", "Client updated Successfully");
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping("/delete-nacta/{seq}")
    @Timed
    public ResponseEntity<Map> deleteRul(@PathVariable Long seq) {
        log.debug("REST request to delete deleteRul : {}", seq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwNactaListService.delete(seq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Client not Found");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/update-repository")
    public ResponseEntity<Map> updateRepository() throws Exception {
        Map<String, String> resp = new HashMap<>();

        try {
            mwNactaListService.updateRepository();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        resp.put("update", "Nacta Repository Updated Successfully");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/upload-nacta")
    @Timed
    public ResponseEntity<Map> uploadNactaLIst(@RequestBody List<MwSancList> sancLists) {
        Map<String, String> resp = new HashMap<>();

        resp.put("ListUpload", mwNactaListService.makeDataSet(sancLists));

        //resp.put("update", "List Uploaded Successfully");
        return ResponseEntity.ok().body(resp);
    }

    // Added by Zohaib Asim - Dated 19-07-2021 CR: Sanction List
    /*@GetMapping("/get-all-file-type-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllFileTypeList(@RequestParam String fileType,
                                                                  @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                  @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Sanc list : {}");
        Map<String, Object> mwRul = mwNactaListService.findAllSancList(fileType, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRul);
    }*/
    /*
     * Modified by Zohaib Asim - Dated 26-07-2021 - CR: Sanction List
     * New Function created
     * */
    @GetMapping("/get-invalid-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getInValidData(@RequestHeader Map<String, Object> reqHeader,
                                                              @RequestParam String fileType,
                                                              @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                              @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All In-Valid data of " + fileType);

        Map<String, Object> mwRul = mwNactaListService.getAllInValidData(fileType, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRul);
    }

    // FIND CLIENTS WHICH MATCHES OUR MWX APPLICATION
    @GetMapping("/find-match-clients")
    public ResponseEntity<Map<String, Object>> getMatchedClients(@RequestParam String fileType) {
        log.debug("REST request to get Matched Clients of " + fileType);

        String prcExeSts = mwNactaListService.findMatchedClients(fileType);

        Map<String, Object> mwRul = new HashMap<>();
        if (prcExeSts.contains("SUCCESS")) {
            mwRul = mwNactaListService.getMatchedClients(fileType);
        }

        return ResponseEntity.ok().body(mwRul);
    }

    @GetMapping("/delete-all-invalid-list")
    public ResponseEntity<Map<String, Object>> deleteAllInValidData(@RequestParam String fileType) {
        log.debug("REST request to Delete All InValid entries of " + fileType);

        Map<String, Object> mwRul = mwNactaListService.deleteAllInValidData(fileType);
        return ResponseEntity.ok().body(mwRul);
    }

    @GetMapping("/find-mtch-and-tag-clnt")
    public ResponseEntity<Map<String, Object>> findMtchAndTagClnt(@RequestParam String tagDtl,
                                                                  @RequestParam Long cnic) {
        log.info("REST request to findMtchAndTagClnt " + cnic);
        Map<String, Object> mapResp = new HashMap<>();
        if (cnic.toString().length() == 13) {
            mapResp = mwNactaListService.findMtchAndTagClnt(tagDtl, cnic);
        } else {
            mapResp.put("Respose", "CNIC must have 13 digits");
        }
        return ResponseEntity.ok().body(mapResp);
    }

    // Dated - 19-02-2022
    @GetMapping("/get-sanc-countries-list")
    public ResponseEntity<List<Map<String, String>>> getSancCountriesList() {
        log.info("REST request to getSancCountriesList ");
        List<Map<String, String>> mapResp = new ArrayList<>();
        mapResp = mwNactaListService.getSancCountriesList();

        return ResponseEntity.ok().body(mapResp);
    }

    /* Commented by Areeba - Dated 01-02-2022 - Another Screen was designed for below functionality
    @GetMapping("/get-tagged-clnt-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getTaggedClntList( @RequestHeader Map<String, Object> reqHeader,
                                                               @RequestParam String fileType,
                                                               @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                               @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Tagged Clients of " + fileType);

        Map<String, Object> mwRul = mwNactaListService.getAllTaggedClnts(fileType, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRul);
    }*/
    // End by Zohaib Asim
}
