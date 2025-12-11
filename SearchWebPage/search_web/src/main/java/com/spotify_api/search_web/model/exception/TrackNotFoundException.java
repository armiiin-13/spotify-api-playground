package com.spotify_api.search_web.model.exception;

public class TrackNotFoundException extends EntityNotFoundException{

    public TrackNotFoundException() {
        super("The track was not found on the repository (and it should be there)");
    }
    
}
