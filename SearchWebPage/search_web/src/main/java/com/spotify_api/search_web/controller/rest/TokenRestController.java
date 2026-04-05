package com.spotify_api.search_web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotify_api.search_web.service.TokenService;

@RestController
@RequestMapping("/spotify")
public class TokenRestController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/user-token")
    public String getUserToken() {
        return tokenService.getUserAccessToken();
    }
}