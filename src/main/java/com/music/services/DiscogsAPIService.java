package com.music.services;

import com.music.mapper.ArtistMapper;
import com.music.mapper.DiscogsAPIMapper;
import com.music.model.Album;
import com.music.response.AlbumResponse;
import com.music.response.TracksResponse;
import com.music.serviceClient.DiscogsServiceClient;
import com.music.serviceClient.MongoDbServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/** DiscogsAPIService class
 *Calls the service clients and builds response for
 * DiscogsAPI API endpoints
 * @author Proma Chowdhury
 * @version 1.0
 */
@Service
public class DiscogsAPIService {

    @Autowired
    DiscogsServiceClient discogsServiceClient;
    @Autowired
    MongoDbServiceClient mongoDbServiceClient;
    @Autowired
    ArtistMapper artistMapper;
    @Autowired
    DiscogsAPIMapper discogsAPIMapper;

    public AlbumResponse getAlbumDetailsForArtist(String artistId, String releaseTitle, String type) {

       HashMap<String,String> trackDetails = discogsServiceClient.getAlbumDetailsForArtist(artistId, releaseTitle, type);

       Album album = mongoDbServiceClient.findAlbumForArtisByAlbumId(artistId, releaseTitle);

        AlbumResponse  albumResponse = artistMapper.mapAlbumToAlbumResponse(album);

        discogsAPIMapper. buildAlbumResponse(album, trackDetails, albumResponse);

        return albumResponse;
    }


    public TracksResponse getTracksAlbumDiscogsAPI(String artist, String releaseTitle, String type) {

        HashMap<String,String> trackDetails = discogsServiceClient.getAlbumDetailsForArtist(artist, releaseTitle, type);

        TracksResponse tracksResponse = new TracksResponse();
        discogsAPIMapper.buildTrackResponse(trackDetails, tracksResponse);

        return tracksResponse;
    }




}
