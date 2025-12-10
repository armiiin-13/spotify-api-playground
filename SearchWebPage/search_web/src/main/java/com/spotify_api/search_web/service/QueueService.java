package com.spotify_api.search_web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify_api.search_web.model.dataStructure.TrackQueue;
import com.spotify_api.search_web.model.entity.Track;
import com.spotify_api.search_web.repository.TrackRepository;

@Component
public class QueueService {
    @Autowired
    private TrackRepository repository;

    private TrackQueue queue;

    public QueueService(){
        this.queue = new TrackQueue();
    }

    public void addTrack(String id){
        this.queue.enqueue(id);
    }

    public Track dequeue(){
        String spotifyId = this.queue.dequeue();
        Optional<Track> op = repository.findById(spotifyId);
        if (op.isPresent()){
            return op.get();
        }
        throw new RuntimeException("Not found on repository");
    }
}
