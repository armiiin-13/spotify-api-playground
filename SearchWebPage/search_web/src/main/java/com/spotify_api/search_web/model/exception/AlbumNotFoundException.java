package com.spotify_api.search_web.model.exception;

public class AlbumNotFoundException extends EntityNotFoundException{

    public AlbumNotFoundException() {
        super("The album was not found on the repository (and it should be there)");
    }
    
}
