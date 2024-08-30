package com.gustavolyra.music_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavolyra.music_api.config.ItunesClient;
import com.gustavolyra.music_api.dto.AlbumDtoResponse;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.dto.MusicDto;
import com.gustavolyra.music_api.entities.Album;
import com.gustavolyra.music_api.entities.Artist;
import com.gustavolyra.music_api.entities.Music;
import com.gustavolyra.music_api.repositories.AlbumRepository;
import com.gustavolyra.music_api.repositories.ArtistRepository;
import com.gustavolyra.music_api.repositories.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ItunesClient itunesClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Transactional()
    public List<MusicDto> getMusics(String name) throws JsonProcessingException {
        ResponseEntity<String> response = itunesClient.info(name, "song");
        String jsonResponse = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode resultNode = jsonNode.get("results");

        List<MusicDto> musicDtos = new ArrayList<>();

        if (resultNode.isArray()) {
            resultNode.forEach(node -> {
                var artistId = node.get("artistId").asLong();
                var albumId = node.get("collectionId").asLong();
                var songId = node.get("trackId").asLong();

                var song = musicRepository.findById(songId);
                var album = albumRepository.findById(albumId);
                var artist = artistRepository.findById(artistId);

                String songName;
                Long milDuration;
                String artistName;
                OffsetDateTime releaseDate;
                String albumName;


                if (song.isPresent()) {
                    songName = song.get().getName();
                    milDuration = song.get().getMilDuration();
                    releaseDate = song.get().getReleaseDate();
                } else {
                    songName = node.get("trackName").asText();
                    milDuration = node.get("trackTimeMillis").asLong();
                    releaseDate = OffsetDateTime.parse(node.get("releaseDate").asText());
                    musicRepository.save(new Music(songId, songName, milDuration, releaseDate));
                }


                if (album.isPresent()) {
                    albumName = album.get().getName();
                    releaseDate = album.get().getReleaseDate();
                } else {
                    albumName = node.get("collectionName").asText();
                    releaseDate = OffsetDateTime.parse(node.get("releaseDate").asText());
                    Album album1 = new Album();
                    album1.setName(albumName);
                    album1.setReleaseDate(releaseDate);
                    album1.setId(albumId);
                    var music = musicRepository.getReferenceById(songId);
                    album1.getAlbumMusics().add(music);
                    albumRepository.save(album1);
                }


                if (artist.isPresent()) {
                    artistName = artist.get().getName();
                } else {
                    artistName = node.get("artistName").asText();
                    var albumRepositoryReferenceById = albumRepository.getReferenceById(albumId);
                    var musicRepositoryReferenceById = musicRepository.getReferenceById(songId);
                    Artist artist1 = new Artist(artistId, artistName);
                    artist1.getMusics().add(musicRepositoryReferenceById);
                    artist1.getAlbums().add(albumRepositoryReferenceById);
                    artistRepository.save(artist1);
                }


                musicDtos.add(new MusicDto(songId, songName, new ArtistDto(artistId, artistName), new AlbumDtoResponse(albumId, albumName, releaseDate), milDuration, releaseDate));
            });
        }

        return musicDtos;
    }
}
