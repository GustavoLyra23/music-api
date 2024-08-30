package com.gustavolyra.music_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavolyra.music_api.config.ItunesClient;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.entities.Artist;
import com.gustavolyra.music_api.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ItunesClient itunesClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Transactional
    public List<ArtistDto> getArtists(String name) throws JsonProcessingException {
        ResponseEntity<String> response = itunesClient.info(name, "musicArtist");
        String jsonResponse = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode resultNode = jsonNode.get("results");

        List<ArtistDto> artists = new ArrayList<>();

        if (resultNode.isArray()) {
            resultNode.forEach(node -> {

                var artistId = node.get("artistId").asLong();
                var artist = artistRepository.findById(artistId);
                String artistName;

                if (artist.isPresent()) {
                    artistName = artist.get().getName();
                } else {
                    artistName = node.get("artistName").asText();
                    artistRepository.save(new Artist(artistId, artistName));
                }

                artists.add(new ArtistDto(artistId, artistName));
            });
        }

        return artists;
    }
}
