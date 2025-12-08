package com.spotify_api.search_web.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Album;
import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.model.SearchResponse;
import com.spotify_api.search_web.model.Track;

@Component
public class SearchService {
    @Autowired
    private SpotifyService spotify;

    private String lastSearch;
    private SearchResponse lastResponse;

    private void getSearch(String search){
        this.lastSearch = search;
        this.lastResponse = spotify.getSearch(search);
    }

    public Set<Artist> getArtistsFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getArtists().getItems();
    }

    public Set<Album> getAlbumsFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getAlbums().getItems();
    }

    public Set<Track> getTracksFromSearch(String search){
        if (!search.equals(lastSearch)){
            getSearch(search);
        }
        return lastResponse.getTracks().getItems();
    }
}
