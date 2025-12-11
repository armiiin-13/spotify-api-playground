package com.spotify_api.search_web.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.repository.TrackRepository;

@Component
public class TrackService {
    @Autowired
    private TrackRepository repository;

    @Autowired
    private DatabaseService database;

    public void saveAll(List<Track> items){
        this.database.saveAllTracks(items);
    }

    public Optional<Track> findTrack(String spotifyId) {
        return this.repository.findById(spotifyId);
    }

}
