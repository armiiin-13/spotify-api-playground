package com.spotify_api.search_web.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.service.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TrackRestController {
    @Autowired
    private TrackService service;

    @GetMapping("/api/tracks")
    public List<Track> getArtistsTracks(@RequestParam String artistId) {
        return this.service.getArtistsTopTen(artistId);
    }
    
}
