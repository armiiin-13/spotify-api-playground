package com.spotify_api.search_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.spotify_api.search_web.model.Album;
import com.spotify_api.search_web.model.Artist;
import com.spotify_api.search_web.model.SearchResponse;
import com.spotify_api.search_web.model.Track;

@Component
public class SpotifyService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    // URL
    private static final String SEARCH_URL = "https://api.spotify.com/v1/search";
    private static final String ARTIST_URL = "https://api.spotify.com/v1/artists/"; // + id
    private static final String ALBUM_URL = "https://api.spotify.com/v1/albums/"; // + id
    private static final String TRACK_URL = "https://api.spotify.com/v1/tracks/"; // + id

    public SearchResponse getSearch(String search){
        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("q", search)
                .queryParam("type", "artist,album,track")
                .queryParam("limit", 5);

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, SearchResponse.class).getBody();
    }

    public Artist getArtistById(String id) {
        // parameters
        String completeUrl = ARTIST_URL + id;
        return getArtistByHref(completeUrl);
    }

    public Artist getArtistByHref(String href){
        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(href, HttpMethod.GET, entity, Artist.class).getBody();
    }

    public Album getAlbumByHref(String href) {
        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(href, HttpMethod.GET, entity, Album.class).getBody();
    }

    public Album getAlbumById(String id){
        String completeUrl = ALBUM_URL + id;
        return getAlbumByHref(completeUrl);
    }

    public Track getTrackByHref(String href) {
        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(href, HttpMethod.GET, entity, Track.class).getBody();
    }

    public Track getTrackById(String id){
        String completeUrl = TRACK_URL + id;
        return getTrackByHref(completeUrl);
    }
}
