package com.music.repository;

import com.music.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**  ArtistRepository Interface
 * Contains Methods for performing certain
 * operations on Artist mongodb object
 * @author Proma Chowdhury
 * @version 1.0
 */
public interface ArtistRepository extends MongoRepository<Artist, String> {

    Artist findArtistByArtistId(String artistId) ;

    List<Artist> findArtistByArtistNameLike(String regex);

    @Override
    Page<Artist> findAll(Pageable pageable);
}
