package com.spotify_api.search_web.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.repository.ArtistRepository;

@Component
public class ArtistService {
    @Autowired
    private ArtistRepository repository;

    public void saveAll(Set<Artist> items) {
        for (Artist artist: items){
            if (!this.repository.existsById(artist.getId())){
                this.repository.save(artist);
            }
        }
    }
}
