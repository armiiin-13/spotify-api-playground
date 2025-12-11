package com.spotify_api.search_web.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Track {
    @Id
    private String id;
    
    private String href;
    private String name;

    private String duration;

    private boolean explicit;

    @JsonProperty("track_number")
    private int number;

    @ManyToOne
    @JsonBackReference
    private Album album;

    @ManyToMany
    private List<Artist> artists;

    // Constructor
    public Track(){}

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

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists(){
        return this.artists;
    }

    public void setArtists(List<Artist> artists){
        this.artists = artists;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("duration_ms")
    public void setDurationMS(int durationMS) {
        if (durationMS != 0) {
            float minutes = durationMS / 60000f;
            this.duration = String.format("%.2f", minutes);
        }
    }
}
