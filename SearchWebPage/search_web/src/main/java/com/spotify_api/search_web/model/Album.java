package com.spotify_api.search_web.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Transient;

@Entity
public class Album {
    @Id
    private String id;

    private String href;
    private String name;

    @OneToMany
    private List<Image> images;

    @JsonProperty("total_tracks")
    private int totalTracks;

    @ManyToMany
    private List<Artist> artists;

    @OneToMany(mappedBy="album", cascade=CascadeType.ALL)
    private List<Track> trackList;

    @Transient
    @JsonProperty("tracks")
    private TrackWrapper trackWrapper;

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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> tracks) {
        this.trackList = tracks;
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    private void resolveTracks() {
        if (trackWrapper != null && trackWrapper.getItems() != null) {
            trackList.clear();
            for (Track t : trackWrapper.getItems()) {
                t.setAlbum(this);
                trackList.add(t);
            }
        }
    }

    private static class TrackWrapper{
        private String href;
        private int total;
        private List<Track> items = new ArrayList<>();

        public TrackWrapper(){}

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Track> getItems() {
            return items;
        }

        public void setItems(List<Track> items) {
            this.items = items;
        }
    }

    public TrackWrapper getTrackWrapper() {
        return trackWrapper;
    }

    public void setTrackWrapper(TrackWrapper trackWrapper) {
        this.trackWrapper = trackWrapper;
    }
}
