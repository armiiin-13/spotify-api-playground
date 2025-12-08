package com.spotify_api.search_web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify_api.search_web.model.Album;

public interface AlbumRepository extends JpaRepository<Album,String>{
    public Optional<Album> findById(String id);
}
