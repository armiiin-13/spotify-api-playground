package com.spotify_api.search_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.Image;
import com.spotify_api.search_web.repository.ImageRepository;

@Component
public class ImageService {
    @Autowired
    private ImageRepository repository;

    public void saveAll(List<Image> items) {
        for (Image image: items){
            this.repository.save(image);
        }
    }
}
