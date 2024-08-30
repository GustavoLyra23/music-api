package com.gustavolyra.music_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Long id;
    private String albumName;
    private OffsetDateTime releaseDate;
    private ArtistDto artist;


}
