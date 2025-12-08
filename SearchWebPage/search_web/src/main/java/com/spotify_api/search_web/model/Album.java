package com.spotify_api.search_web.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Album {
    @Id
    private String id;

    private String href;
    private String name;
    private String image;

    @JsonProperty("total_tracks")
    private int totalTracks;
    

    @ManyToMany
    private Set<Artist> artists;

    @OneToMany(mappedBy="album", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Track> tracks;

    // Constructor
    public Album(){}

    // Getters and Setters
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

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
}
