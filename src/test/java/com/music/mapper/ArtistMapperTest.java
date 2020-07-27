package com.music.mapper;


import com.music.MusicApplication;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.request.AlbumRequest;
import com.music.request.ArtistRequest;
import com.music.response.AlbumResponse;
import com.music.response.AlbumsResponse;
import com.music.response.ArtistResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MusicApplication.class })
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class ArtistMapperTest {

    @InjectMocks
    ArtistMapper artistMapper;
    @Mock
    HttpServletRequest httpServletRequest;

    @Test
    public void testCreateArtist() {

        ArtistRequest artistRequest = new ArtistRequest();
        artistRequest.setAristId("artist123");
        artistRequest.setArtistName("arName");
        Artist artist = artistMapper.createArtist(artistRequest);
        assertEquals(artist.getArtistName(), "arName");
        assertEquals(artist.getArtistId(), "artist123");
    }

    @Test
    public void testCreateAlbum() {

        AlbumRequest albumRequest = new AlbumRequest();
        albumRequest.setAlbumId("alb123");
        albumRequest.setAlbumName("albName");
        albumRequest.setReleaseYear("1990");
        albumRequest.setGenre(Arrays.asList(new String[]{"pop"}));
        Album album = artistMapper.createAlbum(albumRequest);
        assertEquals(album.getAlbumName(), "albName");
        assertEquals(album.getAlbumId(), "alb123");
        assertEquals(album.getReleaseYear(), "1990");
        assertEquals(album.getGenres(), Arrays.asList(new String[]{"pop"}));
    }

    @Test
    public void testMapArtistToArtistResponse() {

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        when(httpServletRequest.getScheme()).thenReturn("http");
        when(httpServletRequest.getServerName()).thenReturn("localhost");
        when(httpServletRequest.getServerPort()).thenReturn(8080);
        ArtistResponse artistResponse = artistMapper.mapArtistToArtistResponse(artist);
        assertEquals(artistResponse.getArtistId(), "ar123");
        assertEquals(artistResponse.getArtistName(), "arName");
        assertEquals(artistResponse.getAlbumsURI().toString(), "http://localhost:8080/artists/ar123/albums");

    }
    @Test
    public void testMapAlbumToAlbumResponse(){

        Album album = new Album();
        album.setAlbumId("alb123");
        album.setAlbumName("albName");
        album.setReleaseYear("1990");
        album.setGenres(Arrays.asList(new String[]{"pop"}));
        AlbumResponse albumResponse = artistMapper.mapAlbumToAlbumResponse(album);
        assertEquals(albumResponse.getAlbumName(), "albName");
        assertEquals(albumResponse.getAlbumId(), "alb123");
        assertEquals(albumResponse.getReleaseYear(), "1990");
        assertEquals(albumResponse.getGenres(), Arrays.asList(new String[]{"pop"}));

        }

     @Test
    public void testMapAlbumsListToAlbumResponseList() {

         Album album = new Album();
         album.setAlbumId("alb123");
         album.setAlbumName("albName");
         album.setReleaseYear("1990");
         album.setGenres(Arrays.asList(new String[]{"pop"}));
         List<Album> albums = new ArrayList<>();
         albums.add(album);
         String artistId = "ar123";
         AlbumsResponse albumResponse = artistMapper
                 .mapAlbumsListToAlbumResponseList(albums, artistId);
         assertEquals(albumResponse.getAlbums().get(0).getAlbumName(), "albName");
         assertEquals(albumResponse.getAlbums().get(0).getAlbumId(), "alb123");
         assertEquals(albumResponse.getAlbums().get(0).getReleaseYear(), "1990");
         assertEquals(albumResponse.getAlbums().get(0).getGenres(), Arrays.asList(new String[]{"pop"}));

     }



}
