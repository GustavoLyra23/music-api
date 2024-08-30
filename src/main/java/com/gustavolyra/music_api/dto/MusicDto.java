package com.gustavolyra.music_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MusicDto {

    private Long id;
    private String songName;
    private ArtistDto artist;
    private AlbumDtoResponse album;
    private Long milDuration;
    private OffsetDateTime releasedDate;

}
