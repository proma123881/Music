package com.music.controller;


import com.music.model.Error;
import com.music.request.ArtistRequest;
import com.music.services.ArtistService;
import com.music.services.DiscogsAPIService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import com.music.config.GlobalVariables;
import com.music.exception.InvalidArtistRequestException;
import com.music.model.Artist;
import com.music.model.SortType;
import com.music.request.AlbumRequest;
import com.music.response.AlbumResponse;
import com.music.response.AlbumsResponse;
import com.music.response.ArtistResponse;
import com.music.response.GetAllArtistResponse;
import com.music.response.MusicAPIResponse;
import com.music.response.TracksResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static com.music.config.GlobalVariables.CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE;
import static com.music.config.GlobalVariables.CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME;
import static com.music.config.GlobalVariables.INVALID_ARTIST_REQUEST_ERROR_CODE;
import static com.music.config.GlobalVariables.INVALID_ARTIST_REQUEST_ERROR_DESC;
import static com.music.config.GlobalVariables.INVALID_ARTIST_REQUEST_ERROR_NAME;
import static com.music.config.GlobalVariables.PARAM_ALBUM_ID;
import static com.music.config.GlobalVariables.PARAM_ARTIST_ID;
import static com.music.config.GlobalVariables.UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE;
import static com.music.config.GlobalVariables.UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC;
import static com.music.config.GlobalVariables.UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/** This class builds all API endpoints.
 * @author Proma Chowdhury
 * @version 1.0
 */
@RestController
@Slf4j
public class ArtistController {

    @Autowired
    ArtistService artistService;
    @Autowired
    DiscogsAPIService discogsAPIService;

    /**
     * Obtains all ARtists
     * @return ResponseEntity
     */
    @RequestMapping(method = GET, value ="/artists", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get list of all artists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved List of Artists"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity<?> getAllArtists( ) {

        log.info("Invoking getAllArtists");
        List<ArtistResponse> artists = artistService.findAllArtists();
        GetAllArtistResponse getAllArtistResponse = new GetAllArtistResponse();
        if(artists != null) {
            getAllArtistResponse.getArtistResponses().addAll(artists);
        }
        log.info("Invoking getAllArtists done");
        return apiResponse(getAllArtistResponse);

    }

    /**
     * Obtains all ARtists filtered by artistName
     * @param searchByRegex
     * @return ResponseEntity
     */
    @RequestMapping(method = GET, value ="/artists/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get List of All artist Search By Artist Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity<?> getAllArtistsFilterByRegex(@RequestParam("search") String searchByRegex) {

        log.info("Invoking getAllArtists with search filter");

        List<ArtistResponse> artists = artistService.findAllArtistRegexFilter(searchByRegex);

        GetAllArtistResponse getAllArtistResponse = new GetAllArtistResponse();
        if(artists != null) {
            getAllArtistResponse.getArtistResponses().addAll(artists);
        }
        log.info("Invoking getAllArtists with search filter done");

        return apiResponse(getAllArtistResponse);

    }
    /**
     * Obtains all ARtists sorted by artistName
     * @param sortType
     * @return ResponseEntity
     */

    @RequestMapping(method = GET, value ="/artists/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get All artists sorted by artist name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity<?> getAllArtistsSorted( @RequestHeader(value = "sort") SortType sortType) {

        log.info("Invoking getAllArtists sorted by artistName");

        List<ArtistResponse> artists = null;

        if(sortType == SortType.ASC) {

            artists = artistService.findAllArtistsAscending();

        }

        if(sortType == SortType.DESC) {


            artists = artistService.findAllArtistsDescending();

        }

        GetAllArtistResponse getAllArtistResponse = new GetAllArtistResponse();
        if(artists != null) {
            getAllArtistResponse.getArtistResponses().addAll(artists);
        }
        log.info("Invoking getAllArtists sorted by artistName done");
        return apiResponse(getAllArtistResponse);

    }

    /**
     * Create Artist
     * @param artistRequest
     * @return ResponseEntity
     */


    @RequestMapping(method = POST, value = "/artists")
    @ApiOperation(value = "Create Artist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created "),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity addArtist(@RequestBody ArtistRequest artistRequest) {

        log.info("Invoking add artist");
        if(artistRequest == null || StringUtils.isBlank(artistRequest.getAristId())) {
            throw new InvalidArtistRequestException
                    (new Error(INVALID_ARTIST_REQUEST_ERROR_CODE, INVALID_ARTIST_REQUEST_ERROR_NAME,
                            INVALID_ARTIST_REQUEST_ERROR_DESC, HttpStatus.BAD_REQUEST),
                            "Please Enter a valid Artist request");
        }
        ArtistResponse artistCreated = artistService.addArtist(artistRequest);
        log.info("Invoking add artist done");
        return apiResponse(artistCreated);
    }
    /**
     * Update Artist
     * @param artistId
     * @param artistRequest
     * @return ResponseEntity
     */


    @RequestMapping(method = PUT, value ="/artists/{artistId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Artist", response = Artist.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity updateArtist(
            @PathVariable(PARAM_ARTIST_ID) String artistId,
            @RequestBody ArtistRequest artistRequest) {

        log.info("Invoking update artist");

        if(artistRequest == null || StringUtils.isBlank(artistId)) {
            throw new InvalidArtistRequestException
                    (new Error(INVALID_ARTIST_REQUEST_ERROR_CODE, INVALID_ARTIST_REQUEST_ERROR_DESC,
                            INVALID_ARTIST_REQUEST_ERROR_DESC, HttpStatus.BAD_REQUEST),
                            "Please Enter a valid Artist request");
        }

        ArtistResponse artistUpdated  = artistService.updateArtist(artistId, artistRequest);

        log.info("Invoking update artist done");
        return apiResponse(artistUpdated);

    }

    /**
     * Create new Album for a given artist
     * @param artistId
     * @return ResponseEntity
     */


    @RequestMapping(method = POST, value ="/artists/{artistId}"+"/albums", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new Album for a given artist", response = Artist.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity createNewAlbumForArtist(
            @PathVariable(PARAM_ARTIST_ID) String artistId,
            @RequestBody AlbumRequest albumRequest) {


        log.info("Invoking create new album for artist");
        if(StringUtils.isBlank(artistId) || albumRequest == null
                || StringUtils.isBlank(albumRequest.getAlbumId()) ) {

            throw new InvalidArtistRequestException
                    (new Error(CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE, CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME,
                            GlobalVariables.CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC, HttpStatus.BAD_REQUEST),
                            "Please Enter a valid request");
        }
        AlbumResponse albumResponse = artistService.createNewAlbumForArtist(artistId,albumRequest);
        log.info("Invoking create new album for artist");
        return apiResponse(albumResponse);
    }

    /**
     * Update Existing Albums for a given artist
     * @param artistId
     * @param albumId
     * @return ResponseEntity
     */

    @RequestMapping(method = PUT, value ="artists/{artistId}/albums/{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Existing Albums for a given artist", response = Artist.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated "),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity updateExistingAlbumForArtist(
            @PathVariable(PARAM_ARTIST_ID) String artistId, @PathVariable(PARAM_ALBUM_ID)String albumId,
            @RequestBody AlbumRequest albumRequest) {

        log.info("Invoking update existing album for artist");

        if(StringUtils.isBlank(artistId) || StringUtils.isBlank(albumId) || albumRequest == null
                || StringUtils.isBlank(albumRequest.getAlbumId()) ) {

            throw new InvalidArtistRequestException
                    (new Error(UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE, UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME,
                            UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC, HttpStatus.BAD_REQUEST),
                            "Please Enter a valid request");
        }
        AlbumResponse albumResponse = artistService.updateExistingAlbum(artistId,albumId, albumRequest);

        log.info("Invoking update existing album for artist done");

        return apiResponse(albumResponse);

    }

    /**
     * Obtains the Albums for a given artist
     * @param artistId
     * @return ResponseEntity
     */

    @RequestMapping(method = GET, value ="artists/{artistId}/albums", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtains the Albums for a given artist", response = AlbumsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity getAlbumForArtist(
            @PathVariable(PARAM_ARTIST_ID) String artistId) {

        log.info("Invoking getAlbumForArtist for artist");


        AlbumsResponse albumsResponse = artistService.getAllAlbumsForArtist(artistId);

        log.info("Invoking getAlbumForArtist for artist done");

        return apiResponse(albumsResponse);

    }
    /**
     * Obtains the Albums for a given artist sorted by releaseyear and Albumname
     * @param artistId
     * @param sortType
     * @return ResponseEntity
     */

    @RequestMapping(method = GET, value ="artists/{artistId}/albums/sort", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtains the Albums for a given artist sorted by releaseyear and Albumname", response = AlbumsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of Albums"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity getAlbumForArtistSorted(
            @PathVariable(PARAM_ARTIST_ID) String artistId,  @RequestParam(value = "sort") SortType sortType) {

        log.info("Invoking getAlbumForArtistSorted for artist");

        AlbumsResponse albumsResponse = artistService.getAllAlbumsForArtistSorted(artistId,sortType);

        log.info("Invoking getAlbumForArtistSorted artist done");

        return apiResponse(albumsResponse);

    }
    /**
     * Obtains the Album details for a given artist filtered by the entered genre
     * @param artistId
     * @param searchByRegex
     * @return ResponseEntity
     */


    @RequestMapping(method = GET, value ="artists/{artistId}/albums/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtains the Album details for a given artist filtered by the entered genre", response = AlbumsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity getAlbumForArtistFilterByGenre(
            @PathVariable(PARAM_ARTIST_ID) String artistId,  @RequestParam("search") String searchByRegex) {

        log.info("InvokinggetAlbumForArtistFilterByGenre artist");

        AlbumsResponse albumsResponse = artistService.findAllAlbumsByGenreRegex(artistId,searchByRegex);

        log.info("Invoking getAlbumForArtistFilterByGenre for artist done");

        return apiResponse(albumsResponse);

    }

    /**
     * Obtains the list of Artist with pagination
     * @param pageable
     * @return ResponseEntity
     */
    @RequestMapping(method = GET, value ="/artists/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtains the list of Artist with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved List of Artists"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public Page<Artist> getAllArtistsPage(Pageable pageable) {

       return artistService.findAllPage(pageable);
    }

    /**
     * Obtains the Album details for a given artist from DiscogsAPI and also musicapi
     * @param albumId
     * @param artistId
     * @param type
     * @return ResponseEntity
     */

    @RequestMapping(method = GET, value = "/artists/{artistId}/albums/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get album details for a artist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved album details for Artist"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity getAlbumDetailsForArtist(@PathVariable("artistId") String artistId,
                                                             @PathVariable("albumId") String albumId,
                                                  @RequestParam("type") String type
                                                            )
    {
        AlbumResponse album = discogsAPIService.getAlbumDetailsForArtist(artistId, albumId, type);
        return apiResponse(album);
    }


    /**
     * Obtains the list of Tracks for a given artist and album
     * @param albumId
     * @param artistId
     * @param type
     * @return ResponseEntity
     */

    @RequestMapping(method = GET, value = "/artists/{artistId}/albums/{albumId}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get album Tracks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of tracks for Album"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    public ResponseEntity getAlbumTracks(@PathVariable("artistId") String artistId,
                                                          @PathVariable("albumId") String albumId,
                                                          @RequestParam("type") String type
    )
    {
        TracksResponse trackResponse = discogsAPIService.getTracksAlbumDiscogsAPI(artistId,albumId, type);
        return apiResponse(trackResponse);
    }



    private static ResponseEntity<?> apiResponse(MusicAPIResponse response) {

        HttpStatus status = HttpStatus.OK;
        if (response != null && response.getErrors() != null && !response.getErrors().isEmpty()) {
            response.getErrors().sort((e1, e2) -> {
                if (e1.getHttpStatus().value() == e2.getHttpStatus().value()) {
                    return 0;
                }
                return e1.getHttpStatus().value() > e2.getHttpStatus().value() ? -1 : 1;
            });

            status = response.getErrors().get(0).getHttpStatus();
        }

        return  ResponseEntity.status(status).cacheControl(CacheControl.noStore().cachePrivate()).body(response);
    }

}
