package com.stackroute.MuzixAssignment.MuzixAppMysql.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.MuzixAssignment.MuzixAppMysql.service.TrackService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    TrackService trackService;

    @InjectMocks
    TrackController trackController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @Test
    public void saveTrackTest() throws Exception {
        Track track = new Track(1,"Pravallika","Manthenna","www.testurl","Stream",10);
        when(trackService.saveTrack(track)).thenReturn(track);
        mockMvc.perform(post("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated());
        verify(trackService, times(1)).saveTrack(Mockito.any(Track.class));
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void getAllTracks() throws Exception{
        List<Track> trackslist = new ArrayList<>();
        Track track= new Track(1,"soch na sake","Arijit","www.testurl","unstreamed",3);
        trackslist.add(track);
        when(trackService.getAllTracks()).thenReturn(trackslist);
        mockMvc.perform(post("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated());
       // verify(trackService, times(1)).getAllTracks();
       // verifyNoMoreInteractions(trackService);
    }

    @Test
    public void deleteTrackTest() throws Exception
    {
        when(trackService.deleteTrack(1)).thenReturn(true);
        mockMvc.perform(delete("/track/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(trackService, times(1)).deleteTrack(1);
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void getTracksByNameTest() throws Exception
    {
        List<Track> tracks = new ArrayList<>();
        Track track1 = new Track(1,"soch na sake","arijith","www.testurl","streamble",54);
        tracks.add(track1);
        when(trackService.getTracksByName("soch na sake")).thenReturn(tracks);
        mockMvc.perform(get("/trackByName?name=soch na sake")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(trackService, times(1)).getTracksByName("soch na sake");
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void updateTrackTest() throws Exception
    {
        Track track1 = new Track(5,"soch na sake","arijith","www.testurl","streamable",44);
        Track track2 = new Track(4,"soch na sake","arijith","www.testurl","streamable",44);
        when(trackService.updateTrack(track1,1)).thenReturn(track2);
        mockMvc.perform(put("/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track1)))
                .andExpect(status().isCreated());
        //verify(trackService, times(1)).updateTrack(Mockito.any(Track.class),1);
        //verifyNoMoreInteractions(trackService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

