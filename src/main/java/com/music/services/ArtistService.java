package com.music.services;

import com.music.mapper.ArtistMapper;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.model.SortType;
import com.music.request.AlbumRequest;
import com.music.request.ArtistRequest;
import com.music.response.AlbumResponse;
import com.music.response.AlbumsResponse;
import com.music.response.ArtistResponse;
import com.music.serviceClient.MongoDbServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** ArtistService class
 *Calls the service clients and builds response for
 * all API endpoints
 * @author Proma Chowdhury
 * @version 1.0
 */
@Service
public class ArtistService {

    @Autowired
    MongoDbServiceClient mongoDbServiceClient;
    @Autowired
    ArtistMapper artistMapper;

    public ArtistResponse addArtist(ArtistRequest artistRequest) {

        Artist artist = artistMapper.createArtist(artistRequest);
        artist = mongoDbServiceClient.saveArtist(artist);
       return artistMapper.mapArtistToArtistResponse(artist);

    }


    public ArtistResponse updateArtist(String artistId, ArtistRequest artistRequest) {

        Artist artist = artistMapper.createArtist(artistRequest);
        artist = mongoDbServiceClient.updateArtist(artistId, artist);
        return artistMapper.mapArtistToArtistResponse(artist);
    }


    public List<ArtistResponse> findAllArtists() {

        List<Artist> artists = mongoDbServiceClient.getArtists();
        List<ArtistResponse> artistResponses = new ArrayList<>();

        artists.forEach(artist ->artistResponses.add(artistMapper.mapArtistToArtistResponse(artist)));

        return artistResponses;



    }



    public List<ArtistResponse> findAllArtistRegexFilter(String nameFilterRegex) {

        List<Artist> artists = mongoDbServiceClient.getArtistsRegex(nameFilterRegex);
        List<ArtistResponse> artistResponses = new ArrayList<>();

        artists.forEach(artist ->artistResponses.add(artistMapper.mapArtistToArtistResponse(artist)));

        return artistResponses;
    }

    public List<ArtistResponse> findAllArtistsAscending() {

        List<Artist> artists = mongoDbServiceClient.getArtistsSortedAscending();
        List<ArtistResponse> artistResponses = new ArrayList<>();

        artists.forEach(artist ->artistResponses.add(artistMapper.mapArtistToArtistResponse(artist)));

        return artistResponses;


    }

    public List<ArtistResponse> findAllArtistsDescending() {

        List<Artist> artists =  mongoDbServiceClient.getArtistsSortedDescending();
        List<ArtistResponse> artistResponses = new ArrayList<>();

        artists.forEach(artist ->artistResponses.add(artistMapper.mapArtistToArtistResponse(artist)));

        return artistResponses;

    }


    public AlbumResponse createNewAlbumForArtist(String artistId, AlbumRequest albumRequest) {

        Album album = artistMapper.createAlbum(albumRequest);
        album = mongoDbServiceClient.addNewAlbumForArtist(artistId, album);
        return artistMapper.mapAlbumToAlbumResponse(album);
    }



     public AlbumResponse updateExistingAlbum(String artistId ,String albumId, AlbumRequest albumRequest) {

         Album album = artistMapper.createAlbum(albumRequest);
         album = mongoDbServiceClient.updateExistingAlbum(artistId, album, albumId );
         return artistMapper.mapAlbumToAlbumResponse(album);

     }



    public AlbumsResponse getAllAlbumsForArtist(String artistId) {

        List<Album> albums  = mongoDbServiceClient.findAllAlbumsForArtist(artistId);
//        List<AlbumResponse> albumResponse = new ArrayList<>();
//        albums.forEach(album ->albumResponse.add(artistMapper.mapAlbumToAlbumResponse(album)));
//        AlbumsResponse albumsResponse = new AlbumsResponse();
//        albumsResponse.setArtistId(artistId);
//        albumsResponse.getAlbums().addAll(albumResponse);
          return artistMapper.mapAlbumsListToAlbumResponseList(albums, artistId);

    }


    public AlbumsResponse getAllAlbumsForArtistSorted(String artistId, SortType sortType) {


        List<Album>  albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName
                    (artistId, sortType);

//        List<AlbumResponse> albumResponse = new ArrayList<>();
//        albums.forEach(album ->albumResponse.add(artistMapper.mapAlbumToAlbumResponse(album)));
//        AlbumsResponse albumsResponse = new AlbumsResponse();
//        albumsResponse.setArtistId(artistId);
//        albumsResponse.getAlbums().addAll(albumResponse);
        return artistMapper.mapAlbumsListToAlbumResponseList(albums, artistId);

    }

    public Page<Artist> findAllPage(Pageable pageable) {

       return mongoDbServiceClient.findAllPage(pageable);
    }

    public AlbumsResponse findAllAlbumsByGenreRegex(String artistId, String regex) {

        List<Album> albums = null;
        albums = mongoDbServiceClient.findAllAbumByGenreRegex(artistId, regex);


        List<AlbumResponse> albumResponse = new ArrayList<>();
        albums.forEach(album ->albumResponse.add(artistMapper.mapAlbumToAlbumResponse(album)));
        AlbumsResponse albumsResponse = new AlbumsResponse();
        albumsResponse.setArtistId(artistId);
        albumsResponse.getAlbums().addAll(albumResponse);
        return albumsResponse;
    }

}
