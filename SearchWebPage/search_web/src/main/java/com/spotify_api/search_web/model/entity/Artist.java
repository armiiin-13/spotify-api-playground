package com.spotify_api.search_web.model.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {
    @Id
    private String id;

    private String href;
    private String name;

    private boolean loaded = false; //default

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(mappedBy="artists", cascade=CascadeType.ALL)
    private List<Album> albums;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Album> singles;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Track> topTracks;

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(List<Track> topTracks) {
        this.topTracks = topTracks;
    }

    public List<Album> getSingles() {
        return singles;
    }

    public void setSingles(List<Album> singles) {
        this.singles = singles;
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
}
