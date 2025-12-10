package com.spotify_api.search_web.model;

import java.util.List;

public class TracksResponse {
    private List<Track> tracks;

    public TracksResponse(){}

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
