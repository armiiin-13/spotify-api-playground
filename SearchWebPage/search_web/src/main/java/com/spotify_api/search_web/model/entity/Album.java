package com.spotify_api.search_web.model.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @JsonProperty("total_tracks")
    private int totalTracks;

    @ManyToMany
    private List<Artist> artists;

    private String artistString;

    @OneToMany(mappedBy="album", cascade=CascadeType.ALL)
    private List<Track> trackList;

    @Transient
    @JsonProperty("tracks")
    private TrackWrapper trackWrapper;

    private boolean loaded = false; // default

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

    public List<Track> getTrackList() {
        return trackList;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setTrackList(List<Track> tracks) {
        this.trackList = tracks;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getArtistString() {
        return artistString;
    }

    public void setArtistString(String artistString) {
        this.artistString = artistString;
    }

    @JsonProperty("images")
    private void stablishImage(List<Image> images) {
        if (images != null && !images.isEmpty()) {
            this.image = images.getFirst();
        }
    }

    @JsonProperty("artists")
    private void stablishArtists(List<Artist> listArtist){
        this.artists = listArtist;
        StringBuilder s = new StringBuilder();
        Iterator<Artist> it = this.artists.iterator();
        while (it.hasNext()){
            s.append(it.next().getName());
            if (it.hasNext()){
                s.append(", ");
            }
        }
        this.artistString = s.toString();
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

        @SuppressWarnings("unused")
        public TrackWrapper(){}

        @SuppressWarnings("unused")
        public String getHref() {
            return href;
        }

        @SuppressWarnings("unused")
        public void setHref(String href) {
            this.href = href;
        }

        @SuppressWarnings("unused")
        public int getTotal() {
            return total;
        }

        @SuppressWarnings("unused")
        public void setTotal(int total) {
            this.total = total;
        }

        public List<Track> getItems() {
            return items;
        }

        @SuppressWarnings("unused")
        public void setItems(List<Track> items) {
            this.items = items;
        }
    }
}
