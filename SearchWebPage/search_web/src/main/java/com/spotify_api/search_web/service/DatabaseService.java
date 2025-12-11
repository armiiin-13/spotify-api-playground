package com.spotify_api.search_web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.entity.Artist;
import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.repository.ImageRepository;

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
    private ImageRepository imageRepository;

    @Autowired
    private SpotifyService spotify;

    @PersistenceContext
    private EntityManager entityManager;

    // ---------------------- ARTISTS ----------------------
    public Artist saveArtist(Artist artist) {
        Artist managed = getManagedArtist(artist.getId(), artist);
        return entityManager.merge(managed);
    }

    private Artist getManagedArtist(String artistId, Artist artistFromSpotify) {
        Artist managed = entityManager.find(Artist.class, artistId);
        if (managed != null) {
            // copy
            managed.setName(artistFromSpotify.getName());
            managed.setImage(artistFromSpotify.getImage());
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
        List<Artist> copy = new ArrayList<>(artists); // <-- copy
        for (Artist artist : copy) {
            Artist managed = entityManager.find(Artist.class, artist.getId());
            if (managed == null) {
                managed = spotify.getArtistByHref(artist.getHref());
            }
            saveArtist(managed);
        }
    }

    public Artist modifyArtist(Artist artist) {
        Artist managed = getManagedArtist(artist.getId(), artist);
        return entityManager.merge(managed);
    }

    // ---------------------- ALBUMS ----------------------
    public Album saveAlbum(Album album) {
        saveAllArtists(album.getArtists());

        Album managed = getManagedAlbum(album.getId(), album);
        return entityManager.merge(managed);
    }

    private Album getManagedAlbum(String albumId, Album albumFromSpotify) {
        Album managed = entityManager.find(Album.class, albumId);
        if (managed != null) {
            //copy
            managed.setName(albumFromSpotify.getName());
            managed.setImage(albumFromSpotify.getImage());
            managed.setArtists(albumFromSpotify.getArtists());
            return managed;
        }
        return albumFromSpotify;
    }

    public void saveAllAlbums(List<Album> albums) {
        if (albums == null)
            return;
        List<Album> copy = new ArrayList<>(albums); // <-- copy
        for (Album album : copy) {
            Album managed = entityManager.find(Album.class, album.getId());
            if (managed == null) {
                managed = spotify.getAlbumByHref(album.getHref());
            }
            saveAlbum(managed);
        }
    }

    public Album modifyAlbum(Album album) {
        Album managed = getManagedAlbum(album.getId(), album);
        return entityManager.merge(managed);
    }

    // ---------------------- TRACKS ----------------------
    public Track saveTrack(Track track) {
        saveAllArtists(track.getArtists());
        saveAlbum(track.getAlbum());

        Track managed = getManagedTrack(track.getId(), track);
        return entityManager.merge(managed);
    }

    public Track saveTrackSimplified(Track track) {
        saveAllArtists(track.getArtists());

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
        List<Track> copy = new ArrayList<>(tracks); // <-- copy
        for (Track track : copy) {
            Track managed = entityManager.find(Track.class, track.getId());
            if (managed == null) {
                saveTrack(track);
            } else {
                saveTrack(managed);
            }
        }
    }

    public void saveAllTracksSimplified(List<Track> tracks) {
        if (tracks == null)
            return;
        List<Track> copy = new ArrayList<>(tracks); // <-- copy
        for (Track track : copy) {
            Track managed = entityManager.find(Track.class, track.getId());
            if (managed == null) {
                saveTrackSimplified(track);
            } else {
                saveTrackSimplified(managed);
            }
        }
    }
}
