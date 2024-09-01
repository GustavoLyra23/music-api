package com.gustavolyra.music_api.repositories;

import com.gustavolyra.music_api.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_album WHERE LOWER(name) LIKE LOWER(CONCAT('%', :inputName, '%')) AND disabled = false;")
    List<Album> findByName(String inputName);


}
