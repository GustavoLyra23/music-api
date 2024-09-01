package com.gustavolyra.music_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavolyra.music_api.config.ItunesClient;
import com.gustavolyra.music_api.dto.AlbumDto;
import com.gustavolyra.music_api.dto.AlbumDtoFromId;
import com.gustavolyra.music_api.dto.AlbumDtoRequest;
import com.gustavolyra.music_api.dto.ArtistDto;
import com.gustavolyra.music_api.entities.Album;
import com.gustavolyra.music_api.entities.Artist;
import com.gustavolyra.music_api.repositories.AlbumRepository;
import com.gustavolyra.music_api.repositories.ArtistRepository;
import com.gustavolyra.music_api.services.exception.DatabaseException;
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


    @Transactional()
    public List<AlbumDto> getAlbums(String name) throws JsonProcessingException {
        ResponseEntity<String> response = itunesClient.info(name, "album");
        String jsonResponse = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode resultNode = jsonNode.get("results");
        List<AlbumDto> albumDtos = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        if (resultNode.isArray()) {
            resultNode.forEach(node -> {
                Long albumId = node.get("collectionId").asLong();
                Long artistId = node.get("artistId").asLong();
                String artistName;
                String albumName;
                OffsetDateTime releaseDate;

                var artist = artistRepository.findById(artistId);
                var album = albumRepository.findById(albumId);


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
                    albumRepository.save(album1);
                }

                if (artist.isPresent()) {
                    artistName = artist.get().getName();
                } else {
                    artistName = node.get("artistName").asText();
                    var albumReference = albumRepository.getReferenceById(albumId);
                    Artist artist1 = new Artist();
                    artist1.setName(artistName);
                    artist1.setId(artistId);
                    artist1.getAlbums().add(albumReference);
                    artistRepository.save(artist1);
                }
                ids.add(albumId);
                ids.add(artistId);
                albumDtos.add(new AlbumDto(albumId, albumName, releaseDate, new ArtistDto(artistId, artistName)));
            });
            var albumLists = albumRepository.findByName(name);
            albumLists.stream().filter(album -> !ids.contains(album.getId())).forEach(album -> {
                var artistReference = album.getArtists().stream().findFirst();
                albumDtos.add(new AlbumDto(album.getId(),
                        album.getName(), album.getReleaseDate(),
                        new ArtistDto(artistReference.get())));
            });
        }
        return albumDtos;
    }

    @Transactional()
    public AlbumDtoFromId findById(Long id) throws JsonProcessingException {
        return null;
    }

    @Transactional
    public void createAlbum(AlbumDtoRequest request) {

        if (!artistRepository.existsById(request.artistId())) {
            throw new DatabaseException("Artist not found");
        }

        if (albumRepository.existsById(request.id())) {
            throw new DatabaseException("Album already exists");
        }

        Album album = new Album();
        album.setId(request.id());
        album.setName(request.albumName());
        album.setReleaseDate(request.releaseDate());
        var artist = artistRepository.findById(request.artistId());
        artist.get().getAlbums().add(album);
        albumRepository.save(album);
        artistRepository.save(artist.get());

    }


}
