package com.stackroute.MuzixAssignment.MuzixAppMysql.service;
//
//
//
//import java.util.List;
//
//public interface TrackService {
//
//
//    public User saveUser(User user);
//
//    public List<User> getAllUsers();
//
//    public User updateUser(User user, int id) throws Exception;
//
//    public boolean deleteuser(int id) throws Exception;
//
//    public List<User> searchUser(String searchString);
//
//    public List<User> getUserByName(String name);
//
//}




import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackAlreadyExistsException;
import com.stackroute.MuzixAssignment.MuzixAppMysql.exceptions.TrackNotFoundException;

import java.util.List;

public interface TrackService {

    public Track saveTrack(Track track) throws TrackAlreadyExistsException;

    public List<Track> getAllTracks();

    public List<Track> getTracksByName(String name);

    public Track updateTrack(Track track, int id) throws TrackNotFoundException;

    public boolean deleteTrack(int id) throws TrackNotFoundException;

    public List<Track> searchTracks(String searchString);
}
