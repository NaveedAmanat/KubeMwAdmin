package com.idev4.admin.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.core.HttpHeaders;

//test http://localhost:8080
//live https://apps.kashf.org:8443

@FeignClient(name = "recoverydisbursementservice", url = "http://localhost:8080/recoverydisbursementservice")
public interface ServiceClient {

    @RequestMapping(value = "/api/death-adjustment-advance/{id}/{prntLoanAppSeq}/{dthDate}")
    ResponseEntity<?> reverseAdvanceRecoveries(@PathVariable(value = "id") Long id, @PathVariable(value = "prntLoanAppSeq") Long prntLoanAppSeq,
                                               @PathVariable(value = "dthDate") String dthDate, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @RequestMapping(value = "/api/death-adjustment-reverse/{clntSeq}")
    ResponseEntity<?> reverseExcessRecoveries(@PathVariable(value = "clntSeq") Long clntSeq,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @RequestMapping(value = "/api/anml-loan-adjust/{loanAppSeq}/{amt}/{anmlRgstrSeq}")
    ResponseEntity<?> adjustAnmlInsClaim(@PathVariable(value = "loanAppSeq") Long loanAppSeq,
                                         @PathVariable(value = "amt") Long amt, @PathVariable(value = "anmlRgstrSeq") Long anmlRgstrSeq,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    //Added by Areeba
    @RequestMapping(value = "/api/dsblty-adjustment-advance/{id}/{prntLoanAppSeq}/{dtOfDsblty}")
    ResponseEntity<?> reverseAdvanceDisabilityRecoveries(@PathVariable(value = "id") Long id, @PathVariable(value = "prntLoanAppSeq") Long prntLoanAppSeq,
                                                         @PathVariable(value = "dtOfDsblty") String dtOfDsblty, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @RequestMapping(value = "/api/dsblty-adjustment-reverse/{clntSeq}")
    ResponseEntity<?> reverseExcessDisabilityRecoveries(@PathVariable(value = "clntSeq") Long clntSeq,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
    //Ended by Areeba
}