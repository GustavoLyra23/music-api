package com.gustavolyra.music_api.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gustavolyra.music_api.dto.AlbumDto;
import com.gustavolyra.music_api.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;


    @GetMapping("/{name}")
    public ResponseEntity<List<AlbumDto>> list(@PathVariable("name") String name) throws JsonProcessingException {
        var albums = albumService.getAlbums(name);
        return ResponseEntity.ok(albums);
    }


}
