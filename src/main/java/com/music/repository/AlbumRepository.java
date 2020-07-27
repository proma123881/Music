package com.music.repository;

import com.music.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

/** AlbumRepository Interface
 * Contains Methods for performing certain
 * operations on Album mongodb object
 * @author Proma Chowdhury
 * @version 1.0
 */
@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {

    public Album findAlbumByAlbumIdAndArtistId(String albumId, String artistId);
    public List<Album> findAlbumByArtistId(String artistId);
    public List<Album> findAlbumByArtistIdAndGenresContains(String artistId, String genre);


}
