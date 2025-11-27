package com.spotify_api.search_web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long internalId;

    private String id;
    private String href;
    private String name;
    private int duration;
    private boolean explicit;
    private int number;

    @ManyToOne
    private Album album;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public Artist getArtist(){
        if (album != null){
            return album.getArtist();
        }
        return null;
    }
}
