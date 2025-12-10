package com.spotify_api.search_web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify_api.search_web.model.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, String>{
    public Optional<Artist> findById(String id);
}
