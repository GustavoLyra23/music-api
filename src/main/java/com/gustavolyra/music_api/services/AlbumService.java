package com.gustavolyra.music_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavolyra.music_api.config.ItunesClient;
import com.gustavolyra.music_api.dto.AlbumDto;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.repositories.AlbumRepository;
import com.gustavolyra.music_api.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {


    @Autowired
    private ItunesClient itunesClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;


    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbums(String name) throws JsonProcessingException {

        ResponseEntity<String> response = itunesClient.albumInfo(name, "album");
        String jsonResponse = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode resultNode = jsonNode.get("results");

        List<AlbumDto> albumDtos = new ArrayList<>();

        if (resultNode.isArray()) {
            resultNode.forEach(node -> {
                Long albumId = node.get("collectionId").asLong();
                Long artistId = node.get("artistId").asLong();

                var artist = artistRepository.findById(artistId);
                var album = albumRepository.findById(albumId);

                String artistName = (artist.isPresent()) ? artist.get().getName() : node.get("artistName").asText();
                String albumName = (album.isPresent()) ? album.get().getName() : node.get("collectionName").asText();
                OffsetDateTime releaseDate = (album.isPresent()) ? album.get().getReleaseDate() : OffsetDateTime.parse(node.get("releaseDate").asText());

                albumDtos.add(new AlbumDto(albumId, albumName, releaseDate, new ArtistDto(artistId, artistName)));
            });
        }

        return albumDtos;
    }


}
