package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.service.MonAccProcessesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
Authored by Areeba
Dated 17-3-2022
Monthly Accounts Reporting
*/

@RestController
@RequestMapping("/api")
public class MonAccProcessesController {

    private final Logger log = LoggerFactory.getLogger(MonAccProcessesController.class);
    private final MonAccProcessesService monAccProcessesService;

    public MonAccProcessesController(MonAccProcessesService monAccProcessesService) {
        this.monAccProcessesService = monAccProcessesService;
    }


    @GetMapping("/get-all-processes")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllProcesses(@RequestHeader Map<String, Object> reqHeader,
                                                               @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                               @RequestParam Boolean isCount) {
        log.debug("REST request to get a page of ProcessTime");
        Map<String, Object> processes = monAccProcessesService.getProcesses(pageIndex, pageSize, isCount);
        return ResponseEntity.ok().body(processes);
    }

    @GetMapping("/run-mon-processes")
    public ResponseEntity<Map<String, Object>> callPrcMonProcesses() {
        log.info("REST request to callPrcMonProcesses ");
        Map<String, Object> mapResp = monAccProcessesService.callPrcMonProcesses();

        return ResponseEntity.ok().body(mapResp);
    }
}
