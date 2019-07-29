package com.stackroute.MuzixAssignment.MuzixAppMysql.service;

import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackAlreadyExistsException;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackNotFoundException;
import com.stackroute.MuzixAssignment.MuzixAppMysql.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

public class TrackServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    TrackRepository trackRepository;

    @InjectMocks
    TrackServiceImpl trackService;

    Track track;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackService).build();
        track = new Track(1,"hamari adhuri kahani","arijith singh","www.testurl","streamable",100);
    }

    @Test
    public void saveTrackTest() throws Exception
    {
        when(trackRepository.save(track)).thenReturn(track);
        Track savedTrack = trackService.saveTrack(track);
        Assert.assertEquals(track,savedTrack);
        verify(trackRepository,times(1)).save(Mockito.any(Track.class));
       // verify(trackRepository,times(1)).existsById(1);
     //   verifyNoMoreInteractions(trackRepository);
    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void saveUserTestFailure() throws TrackAlreadyExistsException {
        when(trackRepository.save((Track)any())).thenReturn(null);
        Track savedTrack = trackService.saveTrack(track);
      //  verify(trackRepository,times(1)).save(Mockito.any(Track.class));
        //verifyNoMoreInteractions(trackRepository);
    }

    @Test
    public void getAllTracksTest()
    {
        List<Track> trackList = new ArrayList<>();
        trackList.add(track);
        when(trackRepository.findAll()).thenReturn(trackList);
        List<Track> retrievedTracks = trackService.getAllTracks();
        Assert.assertEquals(trackList,retrievedTracks);
      //  verify(trackRepository,times(1)).findAll();
        //verifyNoMoreInteractions(trackRepository);
    }

    @Test
    public void getTracksByNameTest()
    {
        List<Track> trackList = new ArrayList<>();
        trackList.add(track);
        when(trackRepository.getTrackByName("majili")).thenReturn(trackList);
        List<Track> retrievedTracks = trackService.getTracksByName("majili");
        Assert.assertEquals(trackList,retrievedTracks);
      //  verify(trackRepository,times(1)).getTrackByName("majili");
        //verifyNoMoreInteractions(trackRepository);
    }

    @Test
    public void updateTrackTest() throws TrackNotFoundException
    {
        Optional<Track> optionalTrack = Optional.of(track);
        when(trackRepository.findById(1)).thenReturn(optionalTrack);
        when(trackRepository.save(track)).thenReturn(track);
        Track updatedTrack = trackService.updateTrack(track,1);
        Assert.assertEquals(track,updatedTrack);
        //verify here verifies that userRepository save method is only called once
        verify(trackRepository,times(1)).save(Mockito.any(Track.class));
        //verify(trackRepository,times(1)).findById(1);
        //verifyNoMoreInteractions(trackRepository);
    }

    @Test
    public void deleteTrackTest() throws TrackNotFoundException
    {
        Optional<Track> optionalTrack = Optional.of(track);
        when(trackRepository.findById(1)).thenReturn(optionalTrack);
        Boolean result = trackService.deleteTrack(1);
        Assert.assertTrue(result);
        verify(trackRepository,times(1)).delete(Mockito.any(Track.class));
      //  verify(trackRepository,times(1)).findById(1);
        //verifyNoMoreInteractions(trackRepository);
    }
}