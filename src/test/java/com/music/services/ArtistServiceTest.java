package com.music.services;


import com.music.MusicApplication;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.request.ArtistRequest;
import com.music.mapper.ArtistMapper;
import com.music.model.SortType;
import com.music.request.AlbumRequest;
import com.music.response.ArtistResponse;
import com.music.serviceClient.MongoDbServiceClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MusicApplication.class })
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class ArtistServiceTest {

    @Mock
    MongoDbServiceClient mongoDbServiceClient;
    @InjectMocks
    ArtistService artistService;
    @Mock
    ArtistRequest artistRequest;
    @Mock
    Artist artist;
    @Mock
    ArtistMapper artistMapper;

   @Test
    public void testAllArtist() {

       ArtistResponse artistResponse = new ArtistResponse();
       List<Artist> artists = new ArrayList<>();
       List<ArtistResponse> artistResponses = new ArrayList<>();
       artist = new Artist();
       when(mongoDbServiceClient.getArtists()).thenReturn(artists);
       when(artistMapper.mapArtistToArtistResponse(artist)).thenReturn(artistResponse);
       artistResponses = artistService.findAllArtists();
       assertNotNull(artistResponses);
       verify(mongoDbServiceClient, times(1)).getArtists();

   }

    @Test
    public void testFindAllArtistRegexFilter() {

        ArtistResponse artistResponse = new ArtistResponse();
        List<Artist> artists = new ArrayList<>();
        List<ArtistResponse> artistResponses = new ArrayList<>();
        artist = new Artist();
        when(mongoDbServiceClient.getArtistsRegex(anyString())).thenReturn(artists);
        when(artistMapper.mapArtistToArtistResponse(artist)).thenReturn(artistResponse);
        artistResponses = artistService.findAllArtistRegexFilter(anyString());
        assertNotNull(artistResponses);
        verify(mongoDbServiceClient, times(1)).getArtistsRegex(anyString());

    }

    @Test
    public void testFindAllArtistSortedAscending() {

        when(mongoDbServiceClient.getArtistsSortedAscending()).thenReturn(new ArrayList<Artist>());
        artistService.findAllArtistsAscending();
        verify(mongoDbServiceClient, times(1)).getArtistsSortedAscending();

    }

    @Test
    public void testFindAllArtistSortedDescending() {

        when(mongoDbServiceClient.getArtistsSortedDescending()).thenReturn(new ArrayList<Artist>());
        artistService.findAllArtistsDescending();
        verify(mongoDbServiceClient, times(1)).getArtistsSortedDescending();

    }

    @Test
    public void testAddArtist() {

        artist = new Artist();
        artistRequest = new ArtistRequest();
        when(mongoDbServiceClient.saveArtist(artist)).thenReturn(artist);
        when(artistMapper.createArtist(artistRequest)).thenReturn(artist);
        artistService.addArtist(artistRequest);
        verify(mongoDbServiceClient, times(1)).saveArtist(artist);

    }

    @Test
    public void testUpdateArtist() {

        artist = new Artist();
        String artistId ="artistId";
        artistRequest = new ArtistRequest();
        when(mongoDbServiceClient.updateArtist(artistId, artist)).thenReturn(artist);
        when(artistMapper.createArtist(artistRequest)).thenReturn(artist);
        artistService.updateArtist(artistId, artistRequest);
        verify(mongoDbServiceClient, times(1)).updateArtist(artistId, artist);

    }

    @Test
    public void testCreateNewAlbumForArtist() {
       String artistId = "ar123";
       Album album = new Album();
        AlbumRequest albumRequest = new AlbumRequest();
        when(mongoDbServiceClient.addNewAlbumForArtist(artistId, album)).thenReturn(album);
        when(artistMapper.createAlbum(albumRequest)).thenReturn(album);
       artistService.createNewAlbumForArtist(artistId, albumRequest);
        verify(mongoDbServiceClient, times(1)).addNewAlbumForArtist(artistId, album);
    }



    @Test
    public void testUpdateAlbumForArtist() {
        String artistId = "ar123";
        Album album = new Album();
        album.setAlbumId("al123");
        AlbumRequest albumRequest = new AlbumRequest();
        when(mongoDbServiceClient.updateExistingAlbum(artistId, album, album.getAlbumId())).thenReturn(album);
        when(artistMapper.createAlbum(albumRequest)).thenReturn(album);
        artistService.updateExistingAlbum(artistId, album.getAlbumId(), albumRequest);
        verify(mongoDbServiceClient, times(1)).updateExistingAlbum(artistId, album, album.getAlbumId());
    }


    @Test
    public void testGetAllAlbum() {
        String artistId = "ar123";
        Album album = new Album();
        album.setAlbumId("al123");
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        AlbumRequest albumRequest = new AlbumRequest();
        when(mongoDbServiceClient.findAllAlbumsForArtist(artistId)).thenReturn(albums);
        when(artistMapper.createAlbum(albumRequest)).thenReturn(album);
        artistService.getAllAlbumsForArtist(artistId);
        verify(mongoDbServiceClient, times(1)).findAllAlbumsForArtist(artistId);
    }


    @Test
    public void testGetAllAlbumSorted() {
        String artistId = "ar123";
        Album album = new Album();
        album.setAlbumId("al123");
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        AlbumRequest albumRequest = new AlbumRequest();
        when(mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName(artistId,SortType.ASC)).thenReturn(albums);
        when(artistMapper.createAlbum(albumRequest)).thenReturn(album);
        artistService.getAllAlbumsForArtistSorted(artistId, SortType.ASC);
        verify(mongoDbServiceClient, times(1)).findAllAlbumsForArtistSortByReleaseYearAndAlbumName(artistId, SortType.ASC);
    }

   @Test
   public void testFindAllAlbumsByGenreRegex() {

       String artistId = "ar123";
       Album album = new Album();
       album.setAlbumId("al123");
       List<Album> albums = new ArrayList<>();
       albums.add(album);
       AlbumRequest albumRequest = new AlbumRequest();
       when(mongoDbServiceClient.findAllAbumByGenreRegex(artistId,"genre")).thenReturn(albums);
       when(artistMapper.createAlbum(albumRequest)).thenReturn(album);
       artistService.findAllAlbumsByGenreRegex(artistId, "genre");
       verify(mongoDbServiceClient, times(1))
               .findAllAbumByGenreRegex(artistId,"genre");
   }

    @Test
    public void testfindAllPage() {

       Page<Artist> artistPage = null;
        when(mongoDbServiceClient.findAllPage(Pageable.unpaged())).thenReturn(artistPage);
        artistService.findAllPage(Pageable.unpaged());
        verify(mongoDbServiceClient, times(1))
                .findAllPage(Pageable.unpaged());
    }

}
