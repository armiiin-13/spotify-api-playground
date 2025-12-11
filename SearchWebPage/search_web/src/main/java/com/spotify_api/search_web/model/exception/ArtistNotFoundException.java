package com.spotify_api.search_web.model.exception;

public class ArtistNotFoundException extends EntityNotFoundException{

    public ArtistNotFoundException() {
        super("The artist was not found on the repository (and it should be there)");
    }
    
}
