package com.music.controller;

import com.music.MusicApplication;
import com.music.exception.InvalidArtistRequestException;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.request.ArtistRequest;
import com.music.response.TracksResponse;
import com.music.services.ArtistService;
import com.music.model.SortType;
import com.music.request.AlbumRequest;
import com.music.response.AlbumResponse;
import com.music.response.AlbumsResponse;
import com.music.response.ArtistResponse;
import com.music.response.GetAllArtistResponse;
import com.music.services.DiscogsAPIService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MusicApplication.class })
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    ArtistController artistController = new ArtistController();
    @Mock
    ArtistService artistService;
    @Mock
    ArtistRequest artistRequest;
    @Mock
    DiscogsAPIService discogsAPIService;

     @Test
    public void testGetAllArtists() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        List<ArtistResponse> artists = new ArrayList<>();
        artists.add(artistResponse);
        when(artistService.findAllArtists()).thenReturn(artists);
        ResponseEntity response = artistController.getAllArtists();
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(1);
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtists();
    }


    @Test
    public void testGetAllArtists_EmptyResponse() {

        List<ArtistResponse> artists = new ArrayList<>();
        when(artistService.findAllArtists()).thenReturn(artists);
        ResponseEntity response = artistController.getAllArtists();
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtists();
    }

    @Test
    public void testGetAllArtists_NullResponse() {

        when(artistService.findAllArtists()).thenReturn(null);
        ResponseEntity response = artistController.getAllArtists();
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtists();
    }


    @Test
    public void testGetAllArtistsSorted_Ascending() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        List<ArtistResponse> artists = new ArrayList<>();
        artists.add(artistResponse);
        when(artistService.findAllArtistsAscending()).thenReturn(artists);
        ResponseEntity response =  artistController.getAllArtistsSorted(SortType.ASC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(1);
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistsAscending();
        verify(artistService, times(0)).findAllArtistsDescending();
    }

    @Test
    public void testGetAllArtistsSorted_Ascending_EmptyResponse() {
        List<ArtistResponse> artists = new ArrayList<>();
        when(artistService.findAllArtistsAscending()).thenReturn(artists);
        ResponseEntity response =  artistController.getAllArtistsSorted(SortType.ASC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistsAscending();
        verify(artistService, times(0)).findAllArtistsDescending();
    }

    @Test
    public void testGetAllArtistsSorted_Ascending_NullResponse() {

        when(artistService.findAllArtistsAscending()).thenReturn(null);
        ResponseEntity response =  artistController.getAllArtistsSorted(SortType.ASC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistsAscending();
        verify(artistService, times(0)).findAllArtistsDescending();
    }

    @Test
    public void testGetAllArtistsSorted_Descending() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        List<ArtistResponse> artists = new ArrayList<>();
        artists.add(artistResponse);
        when(artistService.findAllArtistsDescending()).thenReturn(artists);
        ResponseEntity response = artistController.getAllArtistsSorted(SortType.DESC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(1);
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(0)).findAllArtistsAscending();
        verify(artistService, times(1)).findAllArtistsDescending();
    }

    @Test
    public void testGetAllArtistsSorted_Descending_EmptyResponse() {

        List<ArtistResponse> artists = new ArrayList<>();

        when(artistService.findAllArtistsDescending()).thenReturn(artists);
        ResponseEntity response = artistController.getAllArtistsSorted(SortType.DESC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(0)).findAllArtistsAscending();
        verify(artistService, times(1)).findAllArtistsDescending();
    }

    @Test
    public void testGetAllArtistsSorted_Descending_NullResponse() {

        when(artistService.findAllArtistsDescending()).thenReturn(null);
        ResponseEntity response = artistController.getAllArtistsSorted(SortType.DESC);
        int responseCode = response.getStatusCode().value();
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(0)).findAllArtistsAscending();
        verify(artistService, times(1)).findAllArtistsDescending();
    }


    @Test
    public void testGetAllArtistsSearchByName() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        List<ArtistResponse> artists = new ArrayList<>();
        artists.add(artistResponse);
        when(artistService.findAllArtistRegexFilter(anyString())).thenReturn(artists);
        ResponseEntity response  = artistController.getAllArtistsFilterByRegex(anyString());
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(1);
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistRegexFilter(anyString());
    }

    @Test
    public void testGetAllArtistsSearchByName_EmptyResponse() {

        List<ArtistResponse> artists = new ArrayList<>();
        when(artistService.findAllArtistRegexFilter(anyString())).thenReturn(null);
        ResponseEntity response  = artistController.getAllArtistsFilterByRegex(anyString());
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistRegexFilter(anyString());
    }

    @Test
    public void testGetAllArtistsSearchByName_NullResponse() {

        when(artistService.findAllArtistRegexFilter(anyString())).thenReturn(null);
        ResponseEntity response  = artistController.getAllArtistsFilterByRegex(anyString());
        GetAllArtistResponse getAllArtistResponse = (GetAllArtistResponse)response.getBody();
        assertThat(getAllArtistResponse.getArtistResponses().size()).isEqualTo(0);
        assertThat(getAllArtistResponse.getArtistResponses()).isEqualTo(new ArrayList<>());
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).findAllArtistRegexFilter(anyString());
    }

    @Test
    public void testAddArtists() {
        artistRequest = new ArtistRequest();
        artistRequest.setAristId("AR123");
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        when(artistService.addArtist(artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.addArtist(artistRequest);
        ArtistResponse artistAdded = (ArtistResponse)response.getBody();
        int responseCode = response.getStatusCode().value();
        assertThat(artistAdded).isNotNull();
        assertThat(artistAdded).isEqualTo(artistResponse);
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).addArtist(artistRequest);
    }




    @Test(expected = InvalidArtistRequestException.class)
    public void testAddArtists_NoArtistId_Exception() {
        ArtistRequest artistRequest = new ArtistRequest();

            artistController.addArtist(artistRequest);
    }

    @Test
    public void testAddArtists_EmptyArtistResponse() {
        artistRequest = new ArtistRequest();
        artistRequest.setAristId("Ar123");
        ArtistResponse artistResponse = new ArtistResponse();
        when(artistService.addArtist(artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.addArtist(artistRequest);
        int responseCode = response.getStatusCode().value();
        ArtistResponse artistAdded = (ArtistResponse)response.getBody();
        assertThat(artistAdded).isNotNull();
        assertThat(artistAdded).isEqualTo(artistResponse);
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).addArtist(artistRequest);
    }


    @Test
    public void testAddArtists_NullArtistResponse() {
        artistRequest = new ArtistRequest();
        artistRequest.setAristId("Ar123");
        when(artistService.addArtist(artistRequest)).thenReturn(null);
        ResponseEntity response  = artistController.addArtist(artistRequest);
        int responseCode = response.getStatusCode().value();
        ArtistResponse artistAdded = (ArtistResponse)response.getBody();
        assertThat(artistAdded).isNull();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).addArtist(artistRequest);
    }

    @Test
    public void testUpdateArtists() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        String artistId = "artistId";
        when(artistService.updateArtist(artistId, artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.updateArtist(artistId, artistRequest);
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).updateArtist(artistId, artistRequest);
    }

    @Test
    public void testUpdateArtists_EmptyResponse() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        String artistId = "artistId";
        when(artistService.updateArtist(artistId, artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.updateArtist(artistId, artistRequest);
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).updateArtist(artistId, artistRequest);
    }

    @Test
    public void testUpdateArtists_NullResponse() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        String artistId = "artistId";
        when(artistService.updateArtist(artistId, artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.updateArtist(artistId, artistRequest);
        int responseCode = response.getStatusCode().value();
        assertThat(responseCode).isEqualTo(200);
        verify(artistService, times(1)).updateArtist(artistId, artistRequest);
    }


    @Test(expected = InvalidArtistRequestException.class)
    public void testUpdateArtists_Exception() {
        Artist artist = createArtist(artistRequest);
        ArtistResponse artistResponse = createArtistResponse(artist);
        String artistId = null;
        when(artistService.updateArtist(artistId, artistRequest)).thenReturn(artistResponse);
        ResponseEntity response  = artistController.updateArtist(artistId, artistRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testCreateNewAlbumForArtist() {
        String artistId = "a123";
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = new AlbumResponse();
        albumRequest.setAlbumId("al123");
        when(artistService.createNewAlbumForArtist(artistId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.createNewAlbumForArtist(artistId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        AlbumResponse albumCreated = (AlbumResponse)response.getBody();
        assertThat(albumCreated).isEqualTo(albumResponse);
    }

    @Test
    public void testCreateNewAlbumForArtist_NullResponse() {
        String artistId = "a123";
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = null;
        albumRequest.setAlbumId("al123");
        when(artistService.createNewAlbumForArtist(artistId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.createNewAlbumForArtist(artistId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        AlbumResponse albumCreated = (AlbumResponse)response.getBody();
        assertThat(albumCreated).isNull();
    }


    @Test(expected = InvalidArtistRequestException.class)
    public void testCreateNewAlbumForArtist_Exception_NoArtistId() {
        String artistId = null;
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = null;
        albumRequest.setAlbumId("al123");
        when(artistService.createNewAlbumForArtist(artistId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.createNewAlbumForArtist(artistId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test(expected = InvalidArtistRequestException.class)
    public void testCreateNewAlbumForArtist_Exception_NoAlbumId() {
        String artistId = "Al123";
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = null;
        albumRequest.setAlbumId(null);
        when(artistService.createNewAlbumForArtist(artistId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.createNewAlbumForArtist(artistId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testUpdateAlbumForArtist() {
        String artistId = "a123";
        String albumId ="al123";
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = new AlbumResponse();
        albumRequest.setAlbumId("al123");
        when(artistService.updateExistingAlbum(artistId,albumId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.updateExistingAlbumForArtist(artistId, albumId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        AlbumResponse albumCreated = (AlbumResponse)response.getBody();
        assertThat(albumCreated).isEqualTo(albumResponse);
    }

    @Test
    public void testUpdateAlbumForArtist_NullResponse() {
        String artistId = "a123";
        String albumId ="al123";
        AlbumRequest albumRequest = new AlbumRequest();
        AlbumResponse albumResponse = null;
        albumRequest.setAlbumId("al123");
        when(artistService.updateExistingAlbum(artistId,albumId, albumRequest)).thenReturn(albumResponse);
        ResponseEntity response = artistController.updateExistingAlbumForArtist(artistId, albumId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        AlbumResponse albumCreated = (AlbumResponse)response.getBody();
        assertThat(albumCreated).isNull();
    }


    @Test(expected = InvalidArtistRequestException.class)
    public void testUpdateAlbumForArtist_Exception_NoAristId() {
        String artistId = null;
        String albumId ="al123";
        AlbumRequest albumRequest = new AlbumRequest();
        albumRequest.setAlbumId("al123");
        ResponseEntity response = artistController.updateExistingAlbumForArtist(artistId, albumId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);

    }

    @Test(expected = InvalidArtistRequestException.class)
    public void testUpdateAlbumForArtist_Exception_NoAlbumId() {
        String artistId = "ar123";
        String albumId = null;
        AlbumRequest albumRequest = new AlbumRequest();
        albumRequest.setAlbumId("al123");
        ResponseEntity response = artistController.updateExistingAlbumForArtist(artistId, albumId, albumRequest);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);

    }

    @Test
    public void getAlbum() {
        String artistId = "ar123";
        AlbumsResponse albumsResponse = new AlbumsResponse();
        when(artistService.getAllAlbumsForArtist(artistId)).thenReturn(albumsResponse);
        artistController.getAlbumForArtist(artistId);
        ResponseEntity response = artistController.getAlbumForArtist(artistId);
        AlbumsResponse albumsFetched = (AlbumsResponse)response.getBody();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(albumsFetched).isEqualTo(albumsResponse);
    }


    @Test
    public void getAlbum_SortedAscending() {
        String artistId = "ar123";
        AlbumsResponse albumsResponse = new AlbumsResponse();
        when(artistService.getAllAlbumsForArtistSorted(artistId, SortType.ASC)).thenReturn(albumsResponse);
        artistController.getAlbumForArtist(artistId);
        ResponseEntity response = artistController.getAlbumForArtistSorted(artistId, SortType.ASC);
        AlbumsResponse albumsFetched = (AlbumsResponse)response.getBody();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(albumsFetched).isEqualTo(albumsResponse);
        verify(artistService, times(1)).getAllAlbumsForArtistSorted(artistId, SortType.ASC);

    }

    @Test
    public void getAlbum_FilteredBygenre() {
        String artistId = "ar123";
        AlbumsResponse albumsResponse = new AlbumsResponse();
        when(artistService.findAllAlbumsByGenreRegex(artistId, "genre")).thenReturn(albumsResponse);
        artistController.getAlbumForArtist(artistId);
        ResponseEntity response = artistController.getAlbumForArtistFilterByGenre(artistId, "genre");
        AlbumsResponse albumsFetched = (AlbumsResponse)response.getBody();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(albumsFetched).isEqualTo(albumsResponse);
        verify(artistService, times(1)).findAllAlbumsByGenreRegex(artistId, "genre");

    }

    @Test
    public void testGetTracks() {
        TracksResponse tracksResponse = new TracksResponse();
         when(discogsAPIService.getTracksAlbumDiscogsAPI("artistId", "albumId", "master-release")).thenReturn(tracksResponse);
         ResponseEntity responseEntity = artistController.getAlbumTracks("artistId", "albumId", "master-release");
        TracksResponse tracksFetched = (TracksResponse)responseEntity.getBody();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(tracksFetched).isEqualTo(tracksResponse);
        verify(discogsAPIService, times(1)).getTracksAlbumDiscogsAPI("artistId", "albumId", "master-release");
    }


    @Test
    public void testGetAlbumDetailsForArtist() {
        AlbumResponse albumResponse = new AlbumResponse();
        when(discogsAPIService.getAlbumDetailsForArtist("artistId", "albumId", "master-release")).thenReturn(albumResponse);
        ResponseEntity responseEntity = artistController.getAlbumDetailsForArtist("artistId", "albumId", "master-release");
        AlbumResponse albumFetched = (AlbumResponse)responseEntity.getBody();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(albumResponse).isEqualTo(albumFetched);
        verify(discogsAPIService, times(1)).getAlbumDetailsForArtist("artistId", "albumId", "master-release");
    }

    public static void createArtistRequest(ArtistRequest artistRequest) {
        artistRequest = new ArtistRequest();
        AlbumRequest albumRequest = new AlbumRequest();
        artistRequest.setAristId("artisId");
        artistRequest.setArtistName("artistName");
//        albumRequest = createAlbumRequest(albumRequest);
//        artistRequest.getAlbums().add(albumRequest);

    }


    public static AlbumRequest createAlbumRequest(AlbumRequest albumRequest) {
        albumRequest = new AlbumRequest();
        albumRequest.setAlbumId("albumId1");
        albumRequest.setAlbumName("albumName");
        albumRequest.setGenre(Arrays.asList(new String[]{"pop"}));
        albumRequest.setReleaseYear("1990");
        return albumRequest;
    }
    public static Artist createArtist(ArtistRequest artistRequest) {

        Artist artist = new Artist();
        artist.setArtistId(artistRequest.getAristId());
        artist.setArtistName(artistRequest.getArtistName());
//        if(!artistRequest.getAlbums().isEmpty()) {
//            for(AlbumRequest albumRequest: artistRequest.getAlbums()) {
//
//               Album album = createAlbum(albumRequest);
//                artist.addAlbums(album);
//            }
//
//        }
        return artist;
    }

    public static ArtistResponse createArtistResponse(Artist artist) {

        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setArtistId(artist.getArtistId());
        artistResponse.setArtistName(artist.getArtistName());

        return artistResponse;
    }

    public static Album createAlbum(AlbumRequest albumRequest) {


        Album album = new Album();
        album.setAlbumId(albumRequest.getAlbumId());
        album.setAlbumName(albumRequest.getAlbumName());
        album.setGenres(albumRequest.getGenre());
        album.setReleaseYear(albumRequest.getReleaseYear());
        return album;
    }
}
