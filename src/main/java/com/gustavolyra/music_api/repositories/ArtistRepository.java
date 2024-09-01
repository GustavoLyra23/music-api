package com.gustavolyra.music_api.repositories;

import com.gustavolyra.music_api.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_artist WHERE LOWER(name) LIKE LOWER(CONCAT('%', :inputName, '%')) AND disabled = false;")
    List<Artist> findByName(String inputName);



}
