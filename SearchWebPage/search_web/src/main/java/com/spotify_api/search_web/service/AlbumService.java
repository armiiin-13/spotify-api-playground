package com.spotify_api.search_web.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Album;
import com.spotify_api.search_web.repository.AlbumRepository;

@Component
public class AlbumService {
    @Autowired
    private AlbumRepository repository;

    @Autowired
    private ArtistService artistService;

    public void saveAll(Set<Album> items) {
        for (Album album: items){
            this.save(album);
        }
    }

    public void save(Album album) {
        if (!(this.repository.existsById(album.getId()))){
                this.artistService.saveAll(album.getArtists());
                this.repository.save(album);
            }
    }
}
