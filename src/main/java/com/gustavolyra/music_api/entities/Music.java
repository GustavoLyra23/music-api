package com.gustavolyra.music_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_music")
public class Music {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "albumMusics")
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(mappedBy = "musics")
    private Set<Artist> artists = new HashSet<>();
    private LocalDate releaseDate;
    private Long milDuration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return Objects.equals(id, music.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
