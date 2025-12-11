package com.spotify_api.search_web.model.dataStructure;

import java.util.ArrayList;
import java.util.List;

import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.model.exception.EmptyQueueException;

public class TrackQueue {
    private List<String> queue;

    public TrackQueue(){
        this.queue = new ArrayList<>();
    }

    public void enqueue(String id){
        this.queue.addLast(id);
    }

    public void enqueue(Track track){
        if (track != null){
            this.queue.addLast(track.getId());
        }
    }

    public String dequeue(){
        if (this.queue.isEmpty()){
            throw new EmptyQueueException();
        }
        return this.queue.removeFirst();
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    public String front(){
        if(! this.queue.isEmpty()){
            return this.queue.getFirst();
        }
        return null;
    }
}
