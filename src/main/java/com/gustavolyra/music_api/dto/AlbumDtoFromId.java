package com.gustavolyra.music_api.dto;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class AlbumDtoFromId {

    private Long id;
    private String name;
    private ArtistDto artist;
    private OffsetDateTime releaseDate;
    Set<MusicDto> musicDtoSet = new HashSet<>();

    public AlbumDtoFromId() {
    }

    public AlbumDtoFromId(Long id, String name, ArtistDto artist, OffsetDateTime releaseDate) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }


    public void addMusicDto(MusicDto musicDto) {
        musicDtoSet.add(musicDto);
    }

}
