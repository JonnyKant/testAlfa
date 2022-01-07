package com.example.testAlfa.controller;

import com.example.testAlfa.client.GifBrokeClient;
import com.example.testAlfa.client.GifRichClient;
import com.example.testAlfa.client.HistoricalClient;
import com.example.testAlfa.client.LatestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * todo Document type FeignControllerTest
 */
@SpringBootTest
class FeignControllerTest {
    private ResponseEntity<Map> jsonReturnGif;
    private ResponseEntity<Map> jsonReturnHistorical;
    private ResponseEntity<Map> jsonReturnLatest;
    private String symbols;
    private String dateString;
    @Autowired
    private FeignController feignController;
    @MockBean
    private LatestClient latestClient;
    @MockBean
    private HistoricalClient historicalClient;
    @MockBean
    private GifRichClient gifRichClient;
    @MockBean
    private GifBrokeClient gifBrokeClient;
    Logger logger = LoggerFactory.getLogger(FeignControllerTest.class);


    @BeforeEach
    public void setUp(){
        jsonReturnGif = this.readJSONFromFile("gif.json");
        jsonReturnHistorical = this.readJSONFromFile("historical.json");
        jsonReturnLatest = this.readJSONFromFile("latest.json");

        LocalDate date = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateString = date.format(formatter);

        this.symbols = "RUB";

        Mockito.when(latestClient.getLatest(symbols)).thenReturn(jsonReturnLatest);
        Mockito.when(historicalClient.getHistorical(symbols,dateString)).thenReturn(jsonReturnHistorical);
        Mockito.when(gifRichClient.getRichGif()).thenReturn(jsonReturnGif);
        Mockito.when(gifBrokeClient.getBrokeGif()).thenReturn(jsonReturnGif);
    }


    private ResponseEntity<Map> readJSONFromFile(String s) {

        Path resourceDirectory = Paths.get("src","test","resources", s);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        ResponseEntity<Map> entity;
        logger.info(absolutePath);
        File testJsonRes = new File(absolutePath);
        try {
            Map<String,Object> result = new ObjectMapper().readValue(testJsonRes, HashMap.class);
            entity = new ResponseEntity(result, HttpStatus.OK);
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity = new ResponseEntity(new HashMap<String,Object>(),HttpStatus.BAD_REQUEST);
    }

    @Test
    void getGif(){
        String a = feignController.getGif(symbols);
        Mockito.verify(latestClient, Mockito.times(1)).getLatest(symbols);
        Mockito.verify(historicalClient, Mockito.times(1)).getHistorical(symbols,dateString);
        Mockito.verify(gifBrokeClient, Mockito.times(1)).getBrokeGif();
        Mockito.verify(gifRichClient, Mockito.times(0)).getRichGif();
        assertNotNull(a);
    }
}