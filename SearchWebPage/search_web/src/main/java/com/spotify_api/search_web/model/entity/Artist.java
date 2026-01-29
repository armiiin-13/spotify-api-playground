package com.spotify_api.search_web.model.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Artist {
    @Id
    private String id;

    private String href;
    private String name;

    private boolean loaded = false; //default

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    // Constructor
    public Artist(){}

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref(){
        return this.href;
    }

    public void setHref(String href){
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o instanceof Artist){
            Artist other = (Artist) o;
            return this.name.equals(other.getName());
        }
        return false;
    }

    @JsonProperty("images")
    private void stablishImage(List<Image> images) {
        if (images != null && !images.isEmpty()) {
            this.image = images.getLast();
        }
    }
}
