package com.example.testAlfa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * todo Document type GigRichClient
 */
@FeignClient(name = "gifRich", url = "${feign.client.gif.rich}")
public interface GifRichClient {
    @GetMapping
    ResponseEntity<Map> getRichGif();
}
