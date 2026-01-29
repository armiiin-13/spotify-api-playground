package com.spotify_api.search_web.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.enums.AlbumTypes;

public interface AlbumRepository extends JpaRepository<Album,String>{
    public Optional<Album> findById(String id);

    public Page<Album> findByArtists_IdAndTypeOrderByReleaseYearDesc(String id, AlbumTypes type, Pageable pageable);
}
