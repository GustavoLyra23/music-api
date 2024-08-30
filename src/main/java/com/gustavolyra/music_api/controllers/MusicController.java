package com.gustavolyra.music_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.dto.MusicDto;
import com.gustavolyra.music_api.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/musics")
public class MusicController {


    @Autowired
    private MusicService musicService;

    @GetMapping("/{name}")
    public ResponseEntity<List<MusicDto>> getMusics(@PathVariable("name") String name) throws JsonProcessingException {
        var musics = musicService.getMusics(name);
        return ResponseEntity.ok(musics);
    }

}
