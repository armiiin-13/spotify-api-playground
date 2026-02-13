package com.spotify_api.search_web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spotify_api.search_web.model.entity.Album;
import com.spotify_api.search_web.service.AlbumService;

@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    
    @GetMapping("/album/{id}")
    public String getSearch(Model model, @PathVariable String id) {
        Album album = this.albumService.getAlbum(id);
        model.addAttribute("album", album);
        model.addAttribute("tracks", album.getTrackList());
        return "album-page";
    }
}
