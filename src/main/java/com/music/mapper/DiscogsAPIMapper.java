package com.music.mapper;

import com.music.model.Album;
import com.music.response.TracksResponse;
import com.music.model.Track;
import com.music.response.AlbumResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Mapper class for the DoscogsAPI. This class maps DoscogsAPI response into
 * model classes
 * @author Proma Chowdhury
 * @version 1.0
 */
@Component
public class DiscogsAPIMapper {

    /**
     * Builds AlbumResponse
     * @param album
     * @param albumResponse
     * @param trackDetails
     */
    public void buildAlbumResponse(Album album, HashMap<String,String> trackDetails, AlbumResponse albumResponse){


        if(album != null) {
            if (trackDetails != null || !trackDetails.isEmpty()) {

                Set<Map.Entry<String, String>> trackSet = trackDetails.entrySet();

                    trackSet.forEach(track -> {
                        Track albumTrack = new Track();
                        albumTrack.setTrackNumber(track.getKey());
                        albumTrack.setTrackTitle(track.getValue());
                        albumResponse.getTracks().add(albumTrack);
                    });
                }
            }

    }

    /**
     * Builds TrackResponse
     * @param tracksResponse
     * @param trackDetails
     */

    public void buildTrackResponse(HashMap<String,String> trackDetails, TracksResponse tracksResponse){

        if(trackDetails != null || !trackDetails.isEmpty()) {
            Set<Map.Entry<String, String>> trackSet = trackDetails.entrySet();


                trackSet.forEach(track -> {
                    Track albumTrack = new Track();
                    albumTrack.setTrackNumber(track.getKey());
                    albumTrack.setTrackTitle(track.getValue());
                    tracksResponse.getTracks().add(albumTrack);
                });

        }
    }
}
