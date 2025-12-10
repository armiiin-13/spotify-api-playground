package com.spotify_api.search_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.spotify_api.search_web.model.apiResponse.TokenResponse;

@Component
public class TokenService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String CLIENT_ID = "f2299a8d1b7d40eabd0213156819f1d3";
    private static final String CLIENT_SECRET = "f0cd8ed55492490799de26d1d03087fd";
    private String accessToken;
    private String tokenType;

    private long expiresAt = 0; //timestamp
    private static final String GET_TOKEN_URL = "https://accounts.spotify.com/api/token"; 

    public String getAccessToken(){
        if (this.accessToken == null || this.isExpired()){
            this.refreshToken();
        }
        return this.accessToken;
    }

    public String getTokenType(){
        if (this.accessToken == null || this.isExpired()){
            this.refreshToken();
        }
        return this.tokenType;
    }

    private boolean isExpired(){
        return System.currentTimeMillis() > expiresAt;
    }

    private void refreshToken(){
        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        // body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(GET_TOKEN_URL, HttpMethod.POST, entity, TokenResponse.class);
        this.accessToken = response.getBody().getAccessToken();
        this.tokenType = response.getBody().getTokenType();
        this.expiresAt = System.currentTimeMillis() + (response.getBody().getExpiresIn() * 1000);
    }
}
