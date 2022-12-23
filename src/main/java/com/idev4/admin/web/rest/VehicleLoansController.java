package com.idev4.admin.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.dto.VehicleLoanDto;
import com.idev4.admin.service.VehicleLoansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * @Added, Naveed
 * @Date, 14-09-2022
 * @Description, SCR - Vehicle Loans
 */

@RestController
@RequestMapping("/api")
public class VehicleLoansController {

    private final Logger log = LoggerFactory.getLogger(VehicleLoansController.class);

    @Autowired
    private VehicleLoansService vehicleLoansService;

    @GetMapping("/all-active-vehicle-loans")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllActiveVehicleLoans(@RequestHeader Map<String, Object> reqHeader, @RequestParam Long branchSeq,
                                                                        @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                                        @RequestParam String filter, @RequestParam Boolean isCount) {
        log.debug("all-active-vehicle-loans");
        Map<String, Object> processes = vehicleLoansService.getAllActiveVehicleLoans(branchSeq, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping("/add-vehicle-info")
    @Timed
    public ResponseEntity<Map<String, Object>> AddVehicleInfo(@RequestBody VehicleLoanDto vehicleLoanDto) throws ParseException {
        log.debug("add-vehicle-info");
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> processes = vehicleLoansService.addVehicleInfo(vehicleLoanDto, user);
        return ResponseEntity.ok().body(processes);
    }

    @PutMapping("/update-vehicle-info/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map<String, Object>> updateVehicleInfo(@RequestBody VehicleLoanDto vehicleLoanDto, @PathVariable long loanAppSeq) throws ParseException {
        log.debug("update-vehicle-info");
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> processes = vehicleLoansService.updateVehicleInfo(vehicleLoanDto, loanAppSeq, user);
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping("/image/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok().body(vehicleLoansService.uploadImage(file, file.getOriginalFilename(), user));
    }

    @GetMapping(path = {"/image/download/{loanAppSeq}/{docSeq}"})
    public ResponseEntity<Map<String, Object>> getImage(@PathVariable long loanAppSeq, @PathVariable long docSeq) throws IOException {
        return ResponseEntity.ok().body(vehicleLoansService.downloadImage(loanAppSeq, docSeq));
    }

    @GetMapping(path = {"/image/download/{loanAppSeq}"})
    public ResponseEntity<Map<String, Object>> getImage(@PathVariable long loanAppSeq) throws IOException {
        return ResponseEntity.ok().body(vehicleLoansService.downloadImage(loanAppSeq));
    }

    /* Zohaib Asim - Dated 26-09-2022 */
    @GetMapping(path = {"/get-vehicle-info-by-loan/{loanAppSeq}"})
    public ResponseEntity<Map<String, Object>> getVehicleInfoByLoan(@PathVariable long loanAppSeq) throws IOException {
        return ResponseEntity.ok().body(vehicleLoansService.getVehicleDtlByLoanApp(loanAppSeq));
    }
}
