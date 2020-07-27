package com.music.serviceClient;


import com.music.config.GlobalVariables;
import com.music.exception.MongoDbException;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.model.Error;
import com.music.model.SortType;
import com.music.repository.AlbumRepository;
import com.music.repository.AlbumsRepository;
import com.music.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;


/** MongoDbServiceClient
 *Contains all mongodb operations
 * @author Proma Chowdhury
 * @version 1.0
 */
@Service
public class MongoDbServiceClient {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    AlbumsRepository albumsRepository;
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Artist saveArtist(Artist artist) {
        Artist savedArtist = null;
        try {
            savedArtist = artistRepository.save(artist);
        } catch (Exception e) {
            throw new MongoDbException("Error saving arist to db", e);
        }
        return savedArtist;
    }

    public Artist updateArtist(String artistId, Artist artist) {
        if(artistRepository.findArtistByArtistId(artistId) == null) {
            throw  new MongoDbException(new Error(GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_CODE, GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_NAME,
                    GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_DESC, HttpStatus.NOT_FOUND),"Artist with artistID not in DB");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("artistId").is(artistId));
        Update update = new Update();
        update.set("artistName", artist.getArtistName());
        Artist artistUpdated = null;
        try {
             mongoTemplate.findAndModify(query, update, Artist.class);
             artistUpdated = artistRepository.findArtistByArtistId(artistId);
        } catch (Exception e) {
            throw new MongoDbException("Error updating arist to db", e);
        }
        return artistUpdated;
    }


    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        try {
            artists = artistRepository.findAll();
        } catch (Exception e) {
            throw new MongoDbException("Error retrieving Artist Details from db", e);
        }
        return artists;
    }


    public List<Artist> getArtistsSortedAscending() {

        List<Artist> artists = new ArrayList<>();
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "artistName"));
        try{
        artists = mongoTemplate.find(query, Artist.class);
        } catch (Exception e) {
            throw new MongoDbException("Error retrieving Artist Details from db", e);
        }
        return artists;

    }

    public List<Artist> getArtistsSortedDescending() {
        List<Artist> artists = new ArrayList<>();
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "artistName"));
        try {
            artists = mongoTemplate.find(query, Artist.class);
        } catch (Exception e) {
            throw new MongoDbException("Error retrieving Artist Details from db", e);
        }
        return artists;

    }

    public List<Artist> getArtistsRegex(String regex) {
        List<Artist> artists = new ArrayList<>();
        try {
            artists = artistRepository.findArtistByArtistNameLike(regex);
         } catch (Exception e) {
        throw new MongoDbException("Error retrieving Artist Details from db", e);
    }
    return artists;
    }


    public Album addNewAlbumForArtist(String artistId, Album album) {

        Artist artist = artistRepository.findArtistByArtistId(artistId);
        Album savedAlbum = null;
         if(artist == null) {
             throw  new MongoDbException(new Error(GlobalVariables.MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ARTIST_ERROR_CODE, GlobalVariables.MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ERROR_NAME,
                     GlobalVariables.MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ERROR_DESC, HttpStatus.NOT_FOUND),"Artist with artistID not in DB");
         }

            album.setArtistId(artistId);
            try {

                savedAlbum = albumRepository.save(album);
            } catch (Exception e) {
                throw new MongoDbException("Error saving Album Details to db", e);
            }


        return savedAlbum;


    }

    public Album findAlbumForArtisByAlbumId(String artistId, String albumId ) {

        Album album = null;
        try {
        album = albumRepository.findAlbumByAlbumIdAndArtistId(albumId,artistId);
        }catch (Exception e) {
            throw new MongoDbException("Error updating Album Details to db", e);
        }
        return album;
    }
    public Album updateExistingAlbum(String artistId , Album album, String albumId) {

        if(albumRepository.findAlbumByAlbumIdAndArtistId(albumId,artistId) == null) {
            throw  new MongoDbException(new Error(GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ARTIST_ERROR_CODE, GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ERROR_NAME,
                    GlobalVariables.MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ERROR_DESC, HttpStatus.NOT_FOUND),"Artist with artistID not in DB");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("artistId").is(artistId)).
                addCriteria(Criteria.where("albumId").is(albumId));

        Update update = new Update();
        update.set("albumId", album.getAlbumId());
        update.set("albumName", album.getAlbumName());
        update.set("genres", album.getGenres());
        update.set("releaseYear",album.getReleaseYear());
        try {
        mongoTemplate.findAndModify(query, update, Album.class);
        }catch (Exception e) {
            throw new MongoDbException("Error updating Album Details to db", e);
        }
       return albumRepository.findAlbumByAlbumIdAndArtistId(albumId,artistId);
    }

    public List<Album> findAllAlbumsForArtist(String artistId) {

        if(artistRepository.findArtistByArtistId(artistId) == null) {
            throw  new MongoDbException(new Error(GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_CODE, GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_NAME,
                    GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_DESC, HttpStatus.NOT_FOUND),"Artist with artistID not in DB");
        }

        List<Album> albums = null;
        try {
            albums = albumRepository.findAlbumByArtistId(artistId);
        }catch (Exception e) {

            throw new MongoDbException("Error fetching albums for artist", e);
        }
     return albums;
    }




    public List<Album> findAllAlbumsForArtistSortByReleaseYearAndAlbumName(String artistId, SortType sortType) {

        if(artistRepository.findArtistByArtistId(artistId) == null) {
            throw  new MongoDbException(new Error(GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_CODE, GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_NAME,
                    GlobalVariables.MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_DESC, HttpStatus.NOT_FOUND),"Artist with artistID not in DB");
        }
        Aggregation aggSort = null;

        ProjectionOperation projectionOperation = Aggregation.project("albumName", "albumId", "genres", "releaseYear");

        if(sortType == SortType.DESC) {
            aggSort = newAggregation(match(Criteria.where("artistId").is(artistId)), projectionOperation,
                    sort(Sort.Direction.DESC, "releaseYear").and(Sort.Direction.DESC, "albumName")
            ).withOptions(newAggregationOptions().cursorBatchSize(5).build());
        } else{
            aggSort = newAggregation(match(Criteria.where("artistId").is(artistId)), projectionOperation,
                    sort(Sort.Direction.ASC, "releaseYear").and(Sort.Direction.ASC, "albumName")
            ).withOptions(newAggregationOptions().cursorBatchSize(5).build());
        }

        AggregationResults albumAgg = mongoTemplate.aggregate(aggSort,"album",Album.class);
       List<Album> albums = albumAgg.getMappedResults();
        return albums;
    }

    public List<Album> findAllAbumByGenreRegex(String artistId, String regex) {


        List<Album> albums = null;
        try {
            albums = albumRepository.findAlbumByArtistIdAndGenresContains(artistId,regex);
        }catch(Exception e) {
            throw new MongoDbException("Error fetching albums by Genre regex", e);
        }

        return  albums;
    }

    public Page<Artist> findAllPage(Pageable page) {

        Page<Artist> pageArtist = artistRepository.findAll(page);
        return pageArtist;
    }
}

