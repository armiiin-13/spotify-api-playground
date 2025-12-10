package com.spotify_api.search_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.apiResponse.ItemsPage;
import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.repository.AlbumRepository;

@Component
public class AlbumService {
    @Autowired
    private AlbumRepository repository;

    @Autowired
    private DatabaseService database;

    @Autowired
    private SpotifyService spotify;

    public void saveAll(List<Album> items) {
        this.database.saveAllAlbums(items);
    }

    public List<Album> getAlbumsFromList(List<Album> albums){
        List<Album> list = new ArrayList<>();
        for (Album album: albums){
            Optional<Album> op = repository.findById(album.getId()); 
            if (op.isPresent()){
                list.add(op.get());
            }
        }
        return list;
    }

    public Album getAlbum(String id) {
        Optional<Album> op = this.repository.findById(id);
        if (op.isEmpty()){
            throw new RuntimeException("The album should be in the database");
        }
        Album album = op.get();

        // If the album is not loaded, it should be (loaded = does not have associated albums)
        if (!album.isLoaded()){
            return loadAlbum(album);
        } // else
        return album;
    }

    private Album loadAlbum(Album album){
        // get tracks from album
        ItemsPage<Track> tracks = this.spotify.getAlbumsTracks(album.getId());
        this.database.saveAllTracksSimplified(tracks.getItems());
        album.setTrackList(tracks.getItems());
        
        album.setLoaded(true);
        this.database.modifyAlbum(album);
        return album;
    }
}
