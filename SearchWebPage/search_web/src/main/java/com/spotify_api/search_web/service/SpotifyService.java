package com.spotify_api.search_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.spotify_api.search_web.model.apiResponse.ItemsPage;
import com.spotify_api.search_web.model.apiResponse.SearchResponse;
import com.spotify_api.search_web.model.apiResponse.TracksResponse;
import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.entity.Artist;
import com.spotify_api.search_web.model.entity.Track;

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

    // CONSTANTS
    private static final int SEARCH_LIMIT = 10;
    private static final int TRACKS_LIMIT = 40;

    public SearchResponse getSearch(String search){
        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
            .queryParam("q", search)
            .queryParam("type", "artist,album,track")
            .queryParam("limit", SEARCH_LIMIT);

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, SearchResponse.class).getBody();
    }

    public Artist getArtistById(String id) {
        // parameters
        String uri = ARTIST_URL + id;
        return getArtistByHref(uri);
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
        String uri = ALBUM_URL + id;
        return getAlbumByHref(uri);
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

    public ItemsPage<Album> getArtistsAlbums(String id){
        String uri = ARTIST_URL + id + "/albums";

        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("id", id)
            .queryParam("include_groups", "album");

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ItemsPage<Album>> responseType = new ParameterizedTypeReference<ItemsPage<Album>>() {};
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType).getBody();
    }

    public ItemsPage<Album> getArtistsSingles(String id) {
        String uri = ARTIST_URL + id + "/albums";

        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("id", id)
            .queryParam("include_groups", "single");

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ItemsPage<Album>> responseType = new ParameterizedTypeReference<ItemsPage<Album>>() {};
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType).getBody();
    }

    public TracksResponse getArtistsTopTracks(String id) {
        String uri = ARTIST_URL + id + "/top-tracks";

        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("id", id);

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TracksResponse.class).getBody();
    }

    public ItemsPage<Track> getAlbumsTracks(String id) {
        String uri = ALBUM_URL + id + "/tracks";

        // parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("id", id)
            .queryParam("limit", TRACKS_LIMIT);

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenService.getTokenType() + " " + tokenService.getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ItemsPage<Track>> responseType = new ParameterizedTypeReference<ItemsPage<Track>>() {};
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType).getBody();
    }
}
