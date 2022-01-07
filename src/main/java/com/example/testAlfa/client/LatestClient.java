package com.example.testAlfa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * todo Document type IWeatherClient
 */

@FeignClient(name = "latest", url = "${feign.client.latest.url}")
public interface LatestClient {
    @GetMapping
    ResponseEntity<Map> getLatest(@RequestParam String symbols);
}
