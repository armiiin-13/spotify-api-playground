package com.spotify_api.search_web.controller.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spotify_api.search_web.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/spotify")
public class SpotifyAuthController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(tokenService.buildUserAuthorizationUrl());
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        tokenService.exchangeCodeForUserTokens(code);
        return "redirect:/";
    }
}
