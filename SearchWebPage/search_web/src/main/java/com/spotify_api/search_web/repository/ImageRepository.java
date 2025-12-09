package com.spotify_api.search_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify_api.search_web.model.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{
}