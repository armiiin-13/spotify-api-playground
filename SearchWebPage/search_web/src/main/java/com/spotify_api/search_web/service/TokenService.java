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

    // app client tokens
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String tokenType;

    // user access tokens
    private String userAccessToken;
    private String userRefreshToken;
    private String userTokenType;

    private long userExpiresAt = 0;
    private long expiresAt = 0; // timestamp
    private static final String GET_TOKEN_URL = "https://accounts.spotify.com/api/token";

    private static final String REDIRECT_URI = "http://127.0.0.1:8080/spotify/callback";

    public TokenService() {
        try (BufferedReader br = new BufferedReader(new FileReader("token.txt"))) {
            clientId = br.readLine();
            clientSecret = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken() {
        if (this.accessToken == null || this.isExpired()) {
            this.refreshToken();
        }
        return this.accessToken;
    }

    public String getTokenType() {
        if (this.accessToken == null || this.isExpired()) {
            this.refreshToken();
        }
        return this.tokenType;
    }

    private boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

    private void refreshToken() {
        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        // body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(GET_TOKEN_URL, HttpMethod.POST, entity,
                TokenResponse.class);
        this.accessToken = response.getBody().getAccessToken();
        this.tokenType = response.getBody().getTokenType();
        this.expiresAt = System.currentTimeMillis() + (response.getBody().getExpiresIn() * 1000);
    }

    public String buildUserAuthorizationUrl() {
        String scope = String.join(" ",
                "streaming",
                "user-read-playback-state",
                "user-modify-playback-state");

        return "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + java.net.URLEncoder.encode(clientId, java.nio.charset.StandardCharsets.UTF_8) +
                "&scope=" + java.net.URLEncoder.encode(scope, java.nio.charset.StandardCharsets.UTF_8) +
                "&redirect_uri=" + java.net.URLEncoder.encode(REDIRECT_URI, java.nio.charset.StandardCharsets.UTF_8);
    }

    public void exchangeCodeForUserTokens(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                GET_TOKEN_URL,
                HttpMethod.POST,
                entity,
                TokenResponse.class);

        TokenResponse token = response.getBody();
        this.userAccessToken = token.getAccessToken();
        this.userRefreshToken = token.getRefreshToken();
        this.userTokenType = token.getTokenType();
        this.userExpiresAt = System.currentTimeMillis() + (token.getExpiresIn() * 1000L);
    }

    public String getUserAccessToken() {
        if (this.userAccessToken == null) {
            throw new IllegalStateException("El usuario todavía no ha hecho login con Spotify");
        }

        if (System.currentTimeMillis() > userExpiresAt) {
            refreshUserToken();
        }

        return this.userAccessToken;
    }

    public String getUserTokenType() {
        if (this.userAccessToken == null) {
            throw new IllegalStateException("El usuario todavía no ha hecho login con Spotify");
        }

        if (System.currentTimeMillis() > userExpiresAt) {
            refreshUserToken();
        }

        return this.userTokenType;
    }

    private void refreshUserToken() {
        if (this.userRefreshToken == null) {
            throw new IllegalStateException("No existe refresh token de usuario");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", userRefreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                GET_TOKEN_URL,
                HttpMethod.POST,
                entity,
                TokenResponse.class);

        TokenResponse token = response.getBody();
        this.userAccessToken = token.getAccessToken();
        this.userTokenType = token.getTokenType();
        this.userExpiresAt = System.currentTimeMillis() + (token.getExpiresIn() * 1000L);
    }
}
