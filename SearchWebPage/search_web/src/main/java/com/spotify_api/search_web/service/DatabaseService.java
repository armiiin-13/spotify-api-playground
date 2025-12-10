package com.spotify_api.search_web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spotify_api.search_web.model.Album;
import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.model.Image;
import com.spotify_api.search_web.model.Track;
import com.spotify_api.search_web.repository.AlbumRepository;
import com.spotify_api.search_web.repository.ArtistRepository;
import com.spotify_api.search_web.repository.ImageRepository;
import com.spotify_api.search_web.repository.TrackRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/*
    This service is defined to make all save, modify and remove operations for all repositories
*/
@Service
@Transactional
public class DatabaseService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SpotifyService spotify;

    @PersistenceContext
    private EntityManager entityManager;

    // ---------------------- ARTISTAS ----------------------
    public Artist saveArtist(Artist artist) {
        saveAllImages(artist.getImages());

        Artist managed = getManagedArtist(artist.getId(), artist);
        return entityManager.merge(managed);
    }

    private Artist getManagedArtist(String artistId, Artist artistFromSpotify) {
        Artist managed = entityManager.find(Artist.class, artistId);
        if (managed != null) {
            // copy
            managed.setName(artistFromSpotify.getName());
            managed.setImages(artistFromSpotify.getImages());
            managed.setAlbums(artistFromSpotify.getAlbums());
            managed.setTopTracks(artistFromSpotify.getTopTracks());
            managed.setLoaded(artistFromSpotify.isLoaded());
            return managed;
        }
        return artistFromSpotify;
    }

    public void saveAllArtists(List<Artist> artists) {
        if (artists == null)
            return;
        List<Artist> copy = new ArrayList<>(artists); // <-- copia
        for (Artist artist : copy) {
            Artist managed = entityManager.find(Artist.class, artist.getId());
            if (managed == null) {
                managed = spotify.getArtistByHref(artist.getHref());
            }
            saveArtist(managed);
        }
    }

    // ---------------------- ALBUMS ----------------------
    public Album saveAlbum(Album album) {
        saveAllImages(album.getImages());
        saveAllArtists(album.getArtists());

        Album managed = getManagedAlbum(album.getId(), album);
        return entityManager.merge(managed);
    }

    private Album getManagedAlbum(String albumId, Album albumFromSpotify) {
        Album managed = entityManager.find(Album.class, albumId);
        if (managed != null) {
            //copy
            managed.setName(albumFromSpotify.getName());
            managed.setImages(albumFromSpotify.getImages());
            managed.setArtists(albumFromSpotify.getArtists());
            return managed;
        }
        return albumFromSpotify;
    }

    public void saveAllAlbums(List<Album> albums) {
        if (albums == null)
            return;
        List<Album> copy = new ArrayList<>(albums); // <-- copia
        for (Album album : copy) {
            Album managed = entityManager.find(Album.class, album.getId());
            if (managed == null) {
                managed = spotify.getAlbumByHref(album.getHref());
            }
            saveAlbum(managed);
        }
    }

    // ---------------------- TRACKS ----------------------
    public Track saveTrack(Track track) {
        saveAllArtists(track.getArtists());
        saveAlbum(track.getAlbum());

        Track managed = getManagedTrack(track.getId(), track);
        return entityManager.merge(managed);
    }

    private Track getManagedTrack(String trackId, Track trackFromSpotify) {
        Track managed = entityManager.find(Track.class, trackId);
        if (managed != null) {
            // copy
            managed.setName(trackFromSpotify.getName());
            managed.setArtists(trackFromSpotify.getArtists());
            managed.setAlbum(trackFromSpotify.getAlbum());
            managed.setDuration(trackFromSpotify.getDuration());
            return managed;
        }
        return trackFromSpotify;
    }

    public void saveAllTracks(List<Track> tracks) {
        if (tracks == null)
            return;
        List<Track> copy = new ArrayList<>(tracks); // <-- copia
        for (Track track : copy) {
            Track managed = entityManager.find(Track.class, track.getId());
            if (managed == null) {
                saveTrack(track);
            } else {
                saveTrack(managed);
            }
        }
    }

    // ---------------------- IMAGES ----------------------
    public void saveAllImages(List<Image> images) {
        if (images == null || images.isEmpty())
            return;
        imageRepository.saveAll(images);
    }

    // ---------------------- MODIFY ARTIST ----------------------
    public Artist modifyArtist(Artist artist) {
        Artist managed = getManagedArtist(artist.getId(), artist);
        return entityManager.merge(managed);
    }
}
