package com.stackroute.MuzixAssignment.MuzixAppMysql.repository;
//
//
//        import com.stackroute.MuzixAppMysql.MuzixAppMysqlApplication;
//        import com.stackroute.MuzixAppMysql.domain.User;
//        import org.springframework.data.jpa.repository.JpaRepository;
//        import org.springframework.data.jpa.repository.Query;
//        import org.springframework.stereotype.Repository;
//
//        import java.util.List;
//
//@Repository
//public interface UserRepository extends JpaRepository<User,Integer> {
//
//    @Query("select user from User user where firstName=?1")
//    List<MuzixAppMysqlApplication> userByName(String name);
//
//    @Query(value = "SELECT user FROM User user WHERE name = ?1 OR artist = ?1")
//    List<User> searchUser(String searchString);
//
//   List<User> getUserByName(String name);
//}


import com.stackroute.MuzixAssignment.MuzixAppMysql.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface TrackRepository extends JpaRepository<Track,Integer> {

    @Query("SELECT t FROM Track t WHERE name = ?1")
    List<Track> getTrackByName(String name);

    //@Query(value = "SELECT t FROM Track t WHERE (?1 is null OR name = ?1) AND (?2 = 0 OR id = ?2)")
    @Query(value = "SELECT t FROM Track t WHERE name = ?1 OR artist = ?1")
    List<Track> searchTracks(String searchString);
}