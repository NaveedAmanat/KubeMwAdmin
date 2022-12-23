package com.idev4.admin.feignclient;

import com.idev4.admin.dto.ExpenseDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.HttpHeaders;
import java.util.Map;

@FeignClient(name = "setupservice", url = "http://localhost:8080/setupservice")
public interface SetupFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/reverse-exp/")
    ResponseEntity<Map> reverseMwExp(@RequestBody ExpenseDto expenseDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
