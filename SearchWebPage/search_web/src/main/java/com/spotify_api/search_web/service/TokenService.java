package com.spotify_api.search_web.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String tokenType;

    private long expiresAt = 0; //timestamp
    private static final String GET_TOKEN_URL = "https://accounts.spotify.com/api/token"; 

    public TokenService(){
        try (BufferedReader br = new BufferedReader(new FileReader("Spotify_API\\token.txt"))) {
            clientId = br.readLine();
            clientSecret = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        headers.setBasicAuth(clientId, clientSecret);

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
