package com.music.serviceClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.exception.JsonOperationException;
import com.music.model.Error;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

import static com.music.config.GlobalVariables.JSON_OPERATION_ERROR_CODE;
import static com.music.config.GlobalVariables.JSON_OPERATION_ERROR_DESC;
import static com.music.config.GlobalVariables.JSON_OPERATION_ERROR_NAME;

/** DiscogsServiceClient
 *calls Discogs API endpoint and gets the response
 * @author Proma Chowdhury
 * @version 1.0
 */

@Service
@PropertySource("classpath:application.properties")
public class DiscogsServiceClient {

    @Value("${discogsService.url}")
    private String url;
    @Value("${discogsService.key}")
    private String key ;
    @Value("${discogsService.secret}")
    private String secret ;


    /**
     * Obtains the Album details for a given artist from DiscogsAPI
     * @param artist
     * @param releaseTile
     * @param type
     * @return  HashMap<String, String> of track list
     */
    public  HashMap<String, String> getAlbumDetailsForArtist(String artist, String releaseTile, String type)
    {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);


        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("release_title", releaseTile)
                .queryParam("artist", artist)
                .queryParam("type", type)
                .queryParam("key", key)
                .queryParam("secret", secret);


       ResponseEntity<String> discogsApiResponse = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET,
            new HttpEntity<String>(headers),
                String.class,
            releaseTile,
                artist,type,key,secret

        );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
             root = mapper.readTree(discogsApiResponse.getBody());
        }catch (IOException e) {
           throw new JsonOperationException(new Error(JSON_OPERATION_ERROR_CODE, JSON_OPERATION_ERROR_NAME,
                   JSON_OPERATION_ERROR_DESC, HttpStatus.INTERNAL_SERVER_ERROR),
                   "Error Reading JSON");
        }
        JsonNode results= null;
        JsonNode masterReleaseUrl = null;
        String masterReleaseUrlValue = null;
        if(root != null) {
            results =  root.path("results");
        }
        if(results != null && results.isArray()) {
            masterReleaseUrl = results.path(0).get("resource_url");
            masterReleaseUrlValue = masterReleaseUrl.textValue();
        }




        restTemplate = new RestTemplate();
        ResponseEntity<String> albumsForArtist = null;
        root = null;
        if(StringUtils.isNotBlank(masterReleaseUrlValue)) {
            uriBuilder = UriComponentsBuilder.fromHttpUrl(masterReleaseUrlValue);
            albumsForArtist = restTemplate.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    String.class

            );
        }

        try {
            if(albumsForArtist != null) {
                root = mapper.readTree(albumsForArtist.getBody());
            }
        }catch (IOException e) {

            throw new JsonOperationException(new Error(JSON_OPERATION_ERROR_CODE, JSON_OPERATION_ERROR_NAME,
                    JSON_OPERATION_ERROR_DESC, HttpStatus.INTERNAL_SERVER_ERROR),
                    "Error Reading JSON");
        }
        JsonNode trackList = null;
        if(root != null) {
            trackList = root.path("tracklist");
        }


        return getTrackDetails(trackList);
    }

    /**
     * Builds trach details from  DiscogsAPI
     * @param trackList
     * @return  HashMap<String, String> of track list
     */
    private  HashMap<String, String> getTrackDetails(JsonNode trackList ) {

        HashMap<String, String> trackDetails = new HashMap<>();
       if(trackList != null && trackList.isArray()) {

         trackList.forEach(track -> {JsonNode trackNumber = track.get("position");
         String trackNumberValue = trackNumber.textValue();
         JsonNode trackTile = track.get("title");
         String trackTileValue =trackTile.textValue();

         trackDetails.put(trackNumberValue, trackTileValue);});


       }
       return trackDetails;
    }
}
