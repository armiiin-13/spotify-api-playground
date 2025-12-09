package com.spotify_api.search_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Album;
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

    public Artist getArtist(String id){
        Optional<Artist> op = this.repository.findById(id);
        if (op.isEmpty()){
            throw new RuntimeException("The artist should be in the database");
        }
        Artist artist = op.get();

        // If the artist is not loaded, it should be (loaded = does not have associated albums)
        if (!artist.isLoaded()){
            return loadArtist(artist);
        } // else
        return artist;
    }

    private Artist loadArtist(Artist artist){
        // Get artist's albums
        ItemsPage<Album> albums = this.spotify.getArtistsAlbums(artist.getId());
        this.database.saveAllAlbums(albums.getItems());
        artist.setAlbums(albums.getItems());
        artist.setLoaded(true);
        this.database.modifyArtist(artist);
        return artist;
    }
}
