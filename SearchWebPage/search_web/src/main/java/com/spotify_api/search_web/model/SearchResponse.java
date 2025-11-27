package com.spotify_api.search_web.model;

public class SearchResponse {
    private ItemsPage<Artist> artists;
    private ItemsPage<Album> albums;
    private ItemsPage<Track> tracks;

    // Getters and Setters
    public ItemsPage<Artist> getArtists() {
        return artists;
    }
    public void setArtists(ItemsPage<Artist> artists) {
        this.artists = artists;
    }
    public ItemsPage<Album> getAlbums() {
        return albums;
    }
    public void setAlbums(ItemsPage<Album> albums) {
        this.albums = albums;
    }
    public ItemsPage<Track> getTracks() {
        return tracks;
    }
    public void setTracks(ItemsPage<Track> tracks) {
        this.tracks = tracks;
    } 
}
