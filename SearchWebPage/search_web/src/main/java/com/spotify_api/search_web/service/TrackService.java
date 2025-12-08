package com.spotify_api.search_web.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Track;
import com.spotify_api.search_web.repository.TrackRepository;

@Component
public class TrackService {
    @Autowired
    private TrackRepository repository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistService artistService;

    public void saveAll(Set<Track> items) {
        for (Track track: items){
            if (!(this.repository.existsById(track.getId()))){
                this.albumService.save(track.getAlbum());
                this.artistService.saveAll(track.getArtists());
                this.repository.save(track);
            }
        }
    }
}
