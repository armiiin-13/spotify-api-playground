package com.spotify_api.search_web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify_api.search_web.model.Track;

public interface TrackRepository extends JpaRepository<Track,Long>{
    public Optional<Track> findById(String id);
}
