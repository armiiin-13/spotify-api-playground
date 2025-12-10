package com.spotify_api.search_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.apiResponse.SearchResponse;
import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.entity.Artist;
import com.spotify_api.search_web.model.entity.Track;

@Component
public class SearchService {
    @Autowired
    private SpotifyService spotify;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TrackService trackService;


    private String lastSearch;
    private SearchResponse lastResponse;

    private void getSearch(String search){
        this.lastSearch = search;
        this.lastResponse = spotify.getSearch(search);

        // save all new data to the repositories
        this.artistService.saveAll(this.lastResponse.getArtists().getItems());
        this.albumService.saveAll(this.lastResponse.getAlbums().getItems());
        this.trackService.saveAll(this.lastResponse.getTracks().getItems());

        // convert the albums and artists sets with the whole information
        this.lastResponse.getArtists().setItems(this.artistService.getArtistsFromList(this.lastResponse.getArtists().getItems()));
        this.lastResponse.getAlbums().setItems(this.albumService.getAlbumsFromList(this.lastResponse.getAlbums().getItems()));
    }

    public List<Artist> getArtistsFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getArtists().getItems();
    }

    public List<Album> getAlbumsFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getAlbums().getItems();
    }

    public List<Track> getTracksFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getTracks().getItems();
    }
}
