package com.spotify_api.search_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @GetMapping("/")
    public String getSearch(Model model, @RequestParam(required=false) String search) {
        if (search != null && !search.trim().isEmpty()){
            model.addAttribute("search",search);
        } else {
            model.addAttribute("search", null);
        }
        return "index";
    }
    

}
