package com.spotify_api.search_web.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.repository.ArtistRepository;

@Component
public class ArtistService {
    @Autowired
    private ArtistRepository repository;

    @Autowired
    private SpotifyService spotify;

    public void saveAll(Set<Artist> items) {
        for (Artist artist: items){
            if (!this.repository.existsById(artist.getId())){
                artist = spotify.getArtistByHref(artist.getHref());
                this.repository.save(artist);
            }
        }
    }

    public Set<Artist> getArtistsFromSet(Set<Artist> artists){
        Set<Artist> set = new HashSet<>();
        for (Artist artist: artists){
            Optional<Artist> op = repository.findById(artist.getId()); 
            if (op.isPresent()){
                set.add(op.get());
            }
        }
        return set;
    }
}
