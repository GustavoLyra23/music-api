package com.gustavolyra.music_api.repositories;

import com.gustavolyra.music_api.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
