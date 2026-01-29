package com.spotify_api.search_web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.model.enums.AlbumTypes;
import com.spotify_api.search_web.service.AlbumService;

@RestController
public class AlbumRestController {
    @Autowired
    private AlbumService service;

    @GetMapping("/api/albums")
    public Page<Album> getAlbumsFromArtist(@RequestParam AlbumTypes type, @RequestParam String artist, Pageable pageable){
        return this.service.getAlbumsFromArtist(type, artist, pageable);
    }
}
