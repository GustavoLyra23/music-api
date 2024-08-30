package com.gustavolyra.music_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_album")
public class Album {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "albums")
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_album_music",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
    private Set<Music> albumMusics = new HashSet<>();

    private OffsetDateTime releaseDate;
    private Boolean disabled = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public void addMusic(Music music) {
        albumMusics.add(music);
    }


}
