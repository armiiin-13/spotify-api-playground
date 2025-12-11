package com.spotify_api.search_web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.dataStructure.TrackQueue;
import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.model.exception.TrackNotFoundException;

@Component
public class QueueService {
    @Autowired
    private TrackService trackService;

    private TrackQueue queue;
    private Track nowPlaying;

    public QueueService(){
        this.queue = new TrackQueue();
        this.nowPlaying = null;
    }

    public void addTrack(String id){
        this.queue.enqueue(id);
    }

    public Track dequeue(){
        if (this.queue.isEmpty()){
            return null;
        } else {
            String spotifyId = this.queue.dequeue();
            Optional<Track> op = trackService.findTrack(spotifyId);
            if (op.isPresent()){
                return op.get();
            } // else
            throw new TrackNotFoundException();
        }
    
    }

    public Object nowPlaying() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nowPlaying'");
    }
}
