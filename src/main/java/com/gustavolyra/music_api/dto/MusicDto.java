package com.gustavolyra.music_api.dto;

import lombok.*;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class MusicDto {

    private Long id;
    private String songName;
    private ArtistDto artist;
    private AlbumDtoResponse album;
    private Long milDuration;
    private OffsetDateTime releasedDate;

}
