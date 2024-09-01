package com.gustavolyra.music_api.dto;

import com.gustavolyra.music_api.entities.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {

    private Long id;
    private String name;


    public ArtistDto(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
    }


}
