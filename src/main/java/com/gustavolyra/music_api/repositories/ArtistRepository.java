package com.gustavolyra.music_api.repositories;

import com.gustavolyra.music_api.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
