package com.spotify_api.search_web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.repository.ImageRepository;

@Component
public class ImageService {
    @Autowired
    private ImageRepository repository;
}
