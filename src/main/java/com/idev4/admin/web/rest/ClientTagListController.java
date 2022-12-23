package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.service.ClientTagListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//Added by Areeba - Dated 1-2-2022 - Client Tag List
@RestController
@RequestMapping("/api")
public class ClientTagListController {

    private final Logger log = LoggerFactory.getLogger(ClientTagListController.class);
    private final ClientTagListService clientTagListService;

    public ClientTagListController(ClientTagListService clientTagListService) {
        this.clientTagListService = clientTagListService;
    }


    @GetMapping("/get-tagged-clnt-list")
    @Timed
    public ResponseEntity<Map<String, Object>> getTaggedClntList(@RequestHeader Map<String, Object> reqHeader,
                                                                 @RequestParam String tagType,
                                                                 @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                 @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All Tagged Clients of " + tagType);

        Map<String, Object> mwTag = clientTagListService.getAllTaggedClnts(tagType, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwTag);
    }

    @PutMapping("/change-clnt-tag/{tagDtl}/{clntTagListSeq}")
    @Timed
    public ResponseEntity<Map> changeClntTag(@PathVariable String tagDtl,
                                             @PathVariable Long clntTagListSeq) {
        log.info("REST request to changeClntTag " + clntTagListSeq);
        Map<String, String> mapResp = new HashMap<>();
        mapResp.put("Response", clientTagListService.changeClntTag(tagDtl, clntTagListSeq));
        return ResponseEntity.ok().body(mapResp);
    }
}
//Ended by Areeba
