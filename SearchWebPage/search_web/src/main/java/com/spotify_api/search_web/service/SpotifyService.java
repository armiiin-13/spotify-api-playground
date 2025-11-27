package com.spotify_api.search_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.spotify_api.search_web.model.SearchResponse;

@Component
public class SpotifyService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    // URL
    private static final String SEARCH_URL = "https://api.spotify.com/v1/search";
    private static final String GET_ARTIST_URL = "https://api.spotify.com/v1/artists/"; // + id
    private static final String GET_ALBUM_URL = "https://api.spotify.com/v1/albums/"; // + id
    private static final String GET_TRACK_URL = "https://api.spotify.com/v1/tracks/"; // + id

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
}
