package com.music.mapper;


import com.music.model.Album;
import com.music.model.Artist;
import com.music.request.AlbumRequest;
import com.music.request.ArtistRequest;
import com.music.response.AlbumResponse;
import com.music.response.AlbumsResponse;
import com.music.response.ArtistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Mapper class for the API This class maps request and response into
 * model classes and vice versa
 * @author Proma Chowdhury
 * @version 1.0
 */
@Component
public class ArtistMapper {

    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * Maps ArtistRequest to Artist
     * @param artistRequest
     * @return Artist
     */
    public Artist createArtist(ArtistRequest artistRequest) {

        Artist artist = new Artist();
        if(artistRequest != null) {
            Optional.ofNullable(artistRequest.getAristId()).ifPresent(artistId -> artist.setArtistId(artistId));
            Optional.ofNullable(artistRequest.getArtistName()).ifPresent(artistName -> artist.setArtistName(artistName));
        }

        return artist;
    }


    /**
     * Maps AlbumRequest to Album
     * @param albumRequest
     * @return Album
     */
    public Album createAlbum(AlbumRequest albumRequest) {


        Album album = new Album();
        if(albumRequest != null) {
            Optional.ofNullable(albumRequest.getAlbumId()).ifPresent(albumId -> album.setAlbumId(albumId));
            Optional.ofNullable(albumRequest.getAlbumName()).ifPresent(albumName -> album.setAlbumName(albumName));

            if (albumRequest.getGenre() != null && !albumRequest.getGenre().isEmpty()) {
                album.getGenres().addAll(albumRequest.getGenre());
            }
            Optional.ofNullable(albumRequest.getReleaseYear()).ifPresent(releaseYear -> album.setReleaseYear(releaseYear));
        }
        return album;
    }

    /**
     * Maps Artist to ArtistResponse
     * @param artist
     * @return ArtistResponse
     */
    public ArtistResponse mapArtistToArtistResponse(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse();
        if(artist != null) {
            Optional.ofNullable(artist.getArtistId()).ifPresent(artistId -> artistResponse.setArtistId(artistId));
            Optional.ofNullable(artist.getArtistName()).ifPresent(artistName -> artistResponse.setArtistName(artistName));
            artistResponse.setAlbumsURI(new DefaultUriBuilderFactory().builder()
                    .scheme(httpServletRequest.getScheme())
                    .host(httpServletRequest.getServerName())
                    .port(httpServletRequest.getServerPort())
                    .path("artists/" + artist.getArtistId() + "/albums")
                    .build());
        }
        return artistResponse;

    }


    /**
     * Maps Album to AlbumResponse
     * @param album
     * @return AlbumResponse
     */
    public AlbumResponse mapAlbumToAlbumResponse(Album album) {
        AlbumResponse albumResponse = new AlbumResponse();
        if(album != null) {
            Optional.ofNullable(album.getAlbumId()).ifPresent(albumId -> albumResponse.setAlbumId(albumId));
            Optional.ofNullable(album.getAlbumName()).ifPresent(albumName -> albumResponse.setAlbumName(albumName));
            if (album.getGenres() != null && !album.getGenres().isEmpty()) {
                albumResponse.getGenres().addAll(album.getGenres());
            }
            Optional.ofNullable(album.getReleaseYear()).ifPresent(releaseYear -> albumResponse.setReleaseYear(releaseYear));
        }


        return albumResponse;

    }


    public AlbumsResponse mapAlbumToAlbumsResponse(List<AlbumResponse> albums) {
        AlbumsResponse albumResponse = new AlbumsResponse();
        albumResponse.getAlbums().addAll(albums);

        return albumResponse;

    }

    /**
     * Maps List<Album> to AlbumsResponse
     * @param albums
     * @param artistId
     * @return AlbumsResponse
     */
public AlbumsResponse mapAlbumsListToAlbumResponseList(List<Album> albums, String artistId) {
    List<AlbumResponse> albumResponse = new ArrayList<>();
    if(albums != null || !albums.isEmpty()) {
        albums.forEach(album -> albumResponse.add(mapAlbumToAlbumResponse(album)));
    }
        AlbumsResponse albumsResponse = new AlbumsResponse();
        albumsResponse.setArtistId(artistId);
        albumsResponse.getAlbums().addAll(albumResponse);

    return albumsResponse;

}
}
