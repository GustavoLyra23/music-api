package com.gustavolyra.music_api.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gustavolyra.music_api.dto.AlbumDto;
import com.gustavolyra.music_api.dto.AlbumDtoRequest;
import com.gustavolyra.music_api.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;


    @GetMapping("/{name}")
    public ResponseEntity<List<AlbumDto>> getAlbums(@PathVariable("name") String name) throws JsonProcessingException {
        var albums = albumService.getAlbums(name);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AlbumDto> getAlbumFromId(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Void> createAlbum(@Valid @RequestBody AlbumDtoRequest albumDto) {
        albumService.createAlbum(albumDto);
        return null;
    }


}
