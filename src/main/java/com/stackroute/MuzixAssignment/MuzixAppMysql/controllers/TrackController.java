package com.stackroute.MuzixAssignment.MuzixAppMysql.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Result;
import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackAlreadyExistsException;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackNotFoundException;
import com.stackroute.MuzixAssignment.MuzixAppMysql.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping(value = "api/v1")
@ControllerAdvice(basePackages = "com.stackroute.muzixapp")
public class TrackController {

    //Controller has a dependency on Service class and created a reference
    TrackService trackService;
    
//Parameterized constructor
    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }

    //saveTrack method which is used to store the tracks that are inserted
        @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) {
        ResponseEntity responseEntity;
        try {
            trackService.saveTrack(track);
            responseEntity = new ResponseEntity<String>("Successfully created", HttpStatus.CREATED);
        } catch (Exception exception) {
            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    //saveAllTrack used to store all the tracks
    @PostMapping("alltracks")
    public ResponseEntity<?> saveAllTrack(@RequestBody List<Track> trackList) throws TrackAlreadyExistsException
    {
        List<Track> savedTrackList = new ArrayList<Track>();
        for (Track track:trackList) {
            Track track1 = trackService.saveTrack(track);
            savedTrackList.add(track1);
        }
        return new ResponseEntity<List<Track>>(savedTrackList, HttpStatus.CREATED);
    }

    //getTrackByName method is to get the tracks based on name
    @GetMapping("trackByName")
    public ResponseEntity<?> getTrackByName(@RequestParam String name) throws TrackNotFoundException
    {
        return new ResponseEntity<List<Track>>(trackService.getTracksByName(name), HttpStatus.OK);
    }
    
    //getAllTracks method is to get all the tracks that are inserted
    @GetMapping("track")
    public ResponseEntity<?> getAllTracks() {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity<List<Track>>(trackService.getAllTracks(), HttpStatus.OK);
        }
        catch (Exception exception) {

            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    //updateTrack method is to update any track 
     @PutMapping("track/{id}")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, @PathVariable int id) {
         ResponseEntity responseEntity;
         try {
             trackService.updateTrack(track, id);
             responseEntity = new ResponseEntity<List<Track>>(trackService.getAllTracks(), HttpStatus.CREATED);
         } catch (Exception exception1) {
             responseEntity = new ResponseEntity<String>(exception1.getMessage(), HttpStatus.CONFLICT);
         }
         return responseEntity;
     }

    //deleteTrack is to delete any existing track
      @DeleteMapping("track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable int id) {
        ResponseEntity responseEntity;
        try {
            trackService.deleteTrack(id);
            responseEntity = new ResponseEntity<String>("Successfully deleted", HttpStatus.OK);
        } catch (Exception exception) {
            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
    }
        return responseEntity;
    }

    //searchTrack method is to search for any track
        @GetMapping("searchTracks")
    public ResponseEntity<?> searchTracks(@RequestParam("searchString") String searchString)
    {
        ResponseEntity responseEntity;
        try{
            trackService.searchTracks(searchString);
           responseEntity= new ResponseEntity<List<Track>>(trackService.searchTracks(searchString),HttpStatus.OK);
        }catch (Exception exception){
            responseEntity = new ResponseEntity<String>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("getLastFmTracks")
    public ResponseEntity<?> getLastFmTracks(@RequestParam String url) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject(url,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = objectMapper.readValue(string, Result.class);
        List<Track> trackList = result.results.trackmatches.track;
        List<Track> savedTrackList = new ArrayList<>();
        for (Track track:trackList) {
            Track track1 = trackService.saveTrack(track);
            savedTrackList.add(track1);
        }
        return new ResponseEntity<>(savedTrackList,HttpStatus.OK);
    }
}
