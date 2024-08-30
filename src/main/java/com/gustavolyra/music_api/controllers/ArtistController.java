package com.gustavolyra.music_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.services.ArtistService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;


    @GetMapping("/{name}")
    public ResponseEntity<List<ArtistDto>> getArtists(@PathVariable("name") String name) throws JsonProcessingException {
        var artists = artistService.getArtists(name);
        return ResponseEntity.ok(artists);
    }


}
