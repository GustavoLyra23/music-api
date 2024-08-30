package com.gustavolyra.music_api.repositories;

import com.gustavolyra.music_api.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
