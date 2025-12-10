package com.spotify_api.search_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spotify_api.search_web.model.entity.Artist;
import com.spotify_api.search_web.service.ArtistService;

@Controller
public class ArtistController {
    @Autowired
    private ArtistService artistService;
    
    @GetMapping("/artist/{id}")
    public String getSearch(Model model, @PathVariable String id) {
        Artist artist = artistService.getArtist(id);
        model.addAttribute("artist", artist);
        model.addAttribute("image", artist.getImages().getFirst());

        return "artist-page";
    }
}
