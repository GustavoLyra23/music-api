package com.gustavolyra.music_api.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface ItunesClient {

    @GetExchange("/search")
    ResponseEntity<String> info(@RequestParam("term") String term, @RequestParam("entity") String entity);


}
