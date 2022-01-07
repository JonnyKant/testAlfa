package com.example.testAlfa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * todo Document type l
 */
@FeignClient(name = "gifBroke", url = "${feign.client.gif.broke}")
public interface GifBrokeClient {
    @GetMapping
    ResponseEntity<Map> getBrokeGif();
}

