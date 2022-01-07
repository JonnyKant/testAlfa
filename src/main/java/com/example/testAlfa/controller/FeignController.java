package com.example.testAlfa.controller;

import com.example.testAlfa.client.GifBrokeClient;
import com.example.testAlfa.client.GifRichClient;
import com.example.testAlfa.client.HistoricalClient;
import com.example.testAlfa.client.LatestClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * todo Document type FeignController
 */

@RestController
public class FeignController {
    Logger logger = LoggerFactory.getLogger(FeignController.class);

    private final LatestClient latestClient;
    private final HistoricalClient historicalClient;
    private final GifRichClient gifRichClient;
    private final GifBrokeClient gifBrokeClient;

    @Autowired
    public FeignController(LatestClient latestClient, HistoricalClient historicalClient, GifRichClient gifRichClient,
        GifBrokeClient gifBrokeClient) {
        this.latestClient = latestClient;
        this.historicalClient = historicalClient;
        this.gifRichClient = gifRichClient;
        this.gifBrokeClient = gifBrokeClient;
    }

    @GetMapping("getGif")
    public String getGif(@RequestParam String symbols) {

        LocalDate date = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString  = date.format(formatter);

        JSONObject latest = new JSONObject(latestClient.getLatest(symbols).getBody());
        JSONObject historical = new JSONObject(historicalClient.getHistorical(symbols,dateString).getBody());

        Double l = latest.getJSONObject("rates").getDouble(symbols);
        Double h = historical.getJSONObject("rates").getDouble(symbols);

        int compare = l.compareTo(h);

        JSONObject img;
        if(compare > 0){
            img = new JSONObject(gifRichClient.getRichGif().getBody());
        }
        else {
            img = new JSONObject(gifBrokeClient.getBrokeGif().getBody());
        }
        return img.getJSONObject("data").getJSONObject("images").getJSONObject("original").getString("url");

    }

}

