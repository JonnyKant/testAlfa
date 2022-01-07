package com.example.testAlfa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * todo Document type HistoricalClient
 */

@FeignClient(name = "historical", url = "${feign.client.historical.url}")
public interface HistoricalClient {
    @GetMapping(value = "/{date}.json")
    ResponseEntity<Map> getHistorical(@RequestParam String symbols, @PathVariable("date") String date);
}
