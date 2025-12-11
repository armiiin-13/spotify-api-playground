package com.spotify_api.search_web.model.exception;

public class EmptyQueueException extends RuntimeException{
    public EmptyQueueException(){
        super("Queue is empty");
    }
}
