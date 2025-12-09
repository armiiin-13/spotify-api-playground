package com.spotify_api.search_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Album;
import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.model.Image;
import com.spotify_api.search_web.model.Track;
import com.spotify_api.search_web.repository.AlbumRepository;
import com.spotify_api.search_web.repository.ArtistRepository;
import com.spotify_api.search_web.repository.ImageRepository;
import com.spotify_api.search_web.repository.TrackRepository;

/*
    This service is defined to make all save, modify and remove operations for all repositories
*/
@Component
public class DatabaseService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired // try to find a way to not include this service in this class
    private SpotifyService spotify;

    public void saveArtist(Artist item){ //complete artist
        this.saveAllImages(item.getImages());
        this.artistRepository.save(item);
    }

    public void saveAllArtists(List<Artist> items){
        for (Artist artist: items){
            if (!this.artistRepository.existsById(artist.getId())){
                artist = spotify.getArtistByHref(artist.getHref());
                saveArtist(artist);
            }
        }
    }

    public void saveAlbum(Album item){ //complete album
        this.saveAllImages(item.getImages());
        this.albumRepository.save(item);
        saveAllTracks(item.getTrackList());
    }

    public void saveAllAlbums(List<Album> items){
        for (Album album: items){
            if (!this.albumRepository.existsById(album.getId())){
                album = spotify.getAlbumByHref(album.getHref());
                saveAlbum(album);   
            }
        }
    }

    public void saveTrack(Track item){
        this.saveAllArtists(item.getArtists());
        this.saveAlbum(item.getAlbum());
        this.trackRepository.save(item);
    }

    public void saveAllTracks(List<Track> items){
        this.trackRepository.saveAll(items);
    }

    public void saveAllImages(List<Image> items){
        this.imageRepository.saveAll(items);
    }
}
