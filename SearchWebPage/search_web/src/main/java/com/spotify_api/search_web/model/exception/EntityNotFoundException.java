package com.spotify_api.search_web.model.exception;

public abstract class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
