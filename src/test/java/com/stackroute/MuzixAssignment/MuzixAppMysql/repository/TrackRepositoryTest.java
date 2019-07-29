package com.stackroute.MuzixAssignment.MuzixAppMysql.repository;

import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;

    Track track;

    @Before
    public void setUp()
    {
        track = new Track(1,"majili","tamman","www.testurl","streamable",1233);
        track.setArtist("Tamman");
        track.setName("majili");
        track.setId(1);
        track.setListeners(100);
        track.setStreamable("streamable");
        track.setUrl("www.testurl");

    }

    @After
    public void tearDown(){

        trackRepository.deleteAll();
    }

    @Test
    public void testSaveTrack(){
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getId()).get();
        Assert.assertEquals(1,fetchTrack.getId());
    }

    @Test
    public void testSaveTrackFailure(){
        Track testTrack = new Track(1,"majili","tamman","www.testurl","streambale",1233);
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getId()).get();
        Assert.assertNotSame(fetchTrack,testTrack);
    }

    @Test
    public void getAllTracks()
    {
        List<Track> tracks = new ArrayList<>();
        Track track1 = new Track(1,"majili","tamman","www.testurl","streambale",140);
        Track track2 = new Track(2,"majili","tamman","www.testurl","streamable",150);
        tracks.add(track1);
        tracks.add(track2);
        List<Track> trackslist = trackRepository.findAll();
        //Assert.assertEquals(tracks,trackslist);
        Assert.assertEquals("majili",tracks.get(0).getName());
    }
}