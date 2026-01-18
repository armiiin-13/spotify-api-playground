package com.spotify_api.search_web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spotify_api.search_web.service.SearchService;


@Controller
public class MainController {
    @Autowired
    private SearchService searchService;
    
    @GetMapping("/")
    public String getSearch(Model model, @RequestParam(required=false) String search) {
        if (search != null && !search.trim().isEmpty()){
            model.addAttribute("artists", searchService.getArtistsFromSearch(search));
            model.addAttribute("albums", searchService.getAlbumsFromSearch(search));
            model.addAttribute("tracks", searchService.getTracksFromSearch(search));
        } else {
            model.addAttribute("search", null);
        }
        return "index";
    }
    

}
