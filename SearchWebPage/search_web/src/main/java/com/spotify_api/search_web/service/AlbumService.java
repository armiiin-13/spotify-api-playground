package com.spotify_api.search_web.service;

import java.util.HashSet;
import java.util.Optional;
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

    @Autowired
    private SpotifyService spotify;

    @Autowired
    private ImageService imageService;

    public void saveAll(Set<Album> items) {
        for (Album album: items){
            this.save(album);
        }
    }

    public void save(Album album) {
        if (!(this.repository.existsById(album.getId()))){
                this.artistService.saveAll(album.getArtists());
                album = spotify.getAlbumByHref(album.getHref());
                this.imageService.saveAll(album.getImages());
                this.repository.save(album);
            }
    }

    public Set<Album> getAlbumFromSet(Set<Album> albums){
        Set<Album> set = new HashSet<>();
        for (Album album: albums){
            Optional<Album> op = repository.findById(album.getId()); 
            if (op.isPresent()){
                set.add(op.get());
            }
        }
        return set;
    }
}
