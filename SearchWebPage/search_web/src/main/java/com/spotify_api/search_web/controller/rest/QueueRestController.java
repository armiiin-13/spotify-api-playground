package com.spotify_api.search_web.controller.rest;

import org.springframework.web.bind.annotation.RestController;

import com.spotify_api.search_web.service.QueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class QueueRestController {
    @Autowired 
    private QueueService queue;

    @PostMapping("/queue/{id}")
    @ResponseBody
    public ResponseEntity<Void> addTracktoQueue(@PathVariable String id) {
        queue.addTrack(id);
        return ResponseEntity.ok().build();
    }
    
}
