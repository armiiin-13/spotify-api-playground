package com.spotify_api.search_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.model.ItemsPage;
import com.spotify_api.search_web.repository.ArtistRepository;

@Component
public class ArtistService {
    @Autowired
    private ArtistRepository repository;

    @Autowired
    private SpotifyService spotify;

    @Autowired
    private DatabaseService database;

    public void saveAll(List<Artist> items) {
        this.database.saveAllArtists(items);
    }

    public List<Artist> getArtistsFromList(List<Artist> artists){
        List<Artist> list = new ArrayList<>();
        for (Artist artist: artists){
            Optional<Artist> op = repository.findById(artist.getId()); 
            if (op.isPresent()){
                list.add(op.get());
            }
        }
        return list;
    }
}
