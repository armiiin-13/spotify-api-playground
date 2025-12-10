package com.spotify_api.search_web.model.apiResponse;

import java.util.List;

import com.spotify_api.search_web.model.entity.Track;

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
