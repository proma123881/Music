package com.music.serviceClient;


import com.music.exception.MongoDbException;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.model.SortType;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoDbConfigTest.class})
public class MongoDbServiceClientTest {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private MongoDbServiceClient mongoDbServiceClient;
    @Autowired
    private AlbumRepository albumRepository;


    @Before
    public void setUpContext()  {
        artistRepository.deleteAll();
        albumRepository.deleteAll();
    }

    public void cleanUpRepo()  {
        artistRepository.deleteAll();
        albumRepository.deleteAll();
    }


    @Test
    public void findAllArtist() {

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        List<Artist> artists = artistRepository.findAll();
        assertEquals(artists.size(), 1);
        cleanUpRepo();

    }

    @Test
    public void updateArtist() {
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        List<Artist> artists = artistRepository.findAll();
        assertEquals(artists.size(), 1);
        Artist artistToUpdate = new Artist();
        artistToUpdate.setArtistId("ar123");
        artistToUpdate.setArtistName("arNameUpdated");
        Artist updatedArtist = mongoDbServiceClient.updateArtist("ar123", artistToUpdate);
        assertEquals(updatedArtist.getArtistId(),"ar123");
        assertEquals(updatedArtist.getArtistName(),"arNameUpdated");
        cleanUpRepo();

    }

    @Test(expected = MongoDbException.class)
    public void updateArtist_Exception() {

        Artist artistToUpdate = new Artist();
        artistToUpdate.setArtistId("ar123");
        artistToUpdate.setArtistName("arNameUpdated");
        mongoDbServiceClient.updateArtist("ar123", artistToUpdate);
        cleanUpRepo();

    }

    @Test
    public void testGetAllArtists(){

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
       List<Artist> artists = mongoDbServiceClient.getArtists();
       assertEquals(artists.size(),1);
        cleanUpRepo();
    }


    @Test
    public void testGetAllArtists_NoArtistInDb(){

        List<Artist> artists = mongoDbServiceClient.getArtists();
        assertEquals(artists.size(),0);
        cleanUpRepo();
    }

    @Test
    public void testGetAllArtists_Sorted_ASC(){

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName1");
        artistRepository.save(artist);
        List<Artist> artists = mongoDbServiceClient.getArtistsSortedAscending();
        assertEquals(artists.size(),2);
        assertEquals(artists.get(0).getArtistName(),"arName");
        assertEquals(artists.get(1).getArtistName(),"arName1");
        cleanUpRepo();
    }

    @Test
    public void testGetAllArtists_Sorted_ASC_NoArtistInDB(){


        List<Artist> artists = mongoDbServiceClient.getArtistsSortedAscending();
        assertEquals(artists.size(),0);
        cleanUpRepo();

    }

    @Test
    public void testGetAllArtists_Sorted_DESC(){

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName1");
        artistRepository.save(artist);
        List<Artist> artists = mongoDbServiceClient.getArtistsSortedDescending();
        assertEquals(artists.size(),2);
        assertEquals(artists.get(0).getArtistName(),"arName1");
        assertEquals(artists.get(1).getArtistName(),"arName");
        cleanUpRepo();
    }

    @Test
    public void testGetAllArtists_Sorted_DESC_NoRecordInDB(){

      List<Artist> artists = mongoDbServiceClient.getArtistsSortedDescending();
        assertEquals(artists.size(),0);
        cleanUpRepo();

    }

    @Test
    public void testGetAllArtistRegex(){
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        artist = new Artist();
        artist.setArtistId("br123");
        artist.setArtistName("brName1");
        artistRepository.save(artist);
        List<Artist> artists = mongoDbServiceClient.getArtistsRegex("b");
        assertEquals(artists.size(),1);
        assertEquals(artists.get(0).getArtistName(), "brName1");
        cleanUpRepo();
    }

    @Test
    public void testGetAllArtistRegex_NoRecordInDb(){
        List<Artist> artists = mongoDbServiceClient.getArtistsRegex("b");
        assertEquals(artists.size(),0);
        cleanUpRepo();
    }

    @Test
    public void testGetAllArtistRegex_NoRecordMatchInDB(){
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        artist = new Artist();
        artist.setArtistId("br123");
        artist.setArtistName("brName1");
        artistRepository.save(artist);
        List<Artist> artists = mongoDbServiceClient.getArtistsRegex("c");
        assertEquals(artists.size(),0);
        cleanUpRepo();

    }


    @Test
    public void testAddAlbumForArtist(){
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setAlbumId("al123");
        album.setAlbumName("alName");
        mongoDbServiceClient.addNewAlbumForArtist(artist.getArtistId(), album);
        List<Album> albums = albumRepository.findAll();
        assertEquals(albums.size(), 1);
        cleanUpRepo();

    }

    @Test(expected = MongoDbException.class)
    public void testAddAlbumForArtist_Exception_MismatchArtistId(){
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setAlbumId("al123");
        album.setAlbumName("alName");
        mongoDbServiceClient.addNewAlbumForArtist("C", album);
        cleanUpRepo();



    }

    @Test(expected = MongoDbException.class)
    public void testAddAlbumForArtist_Exception_NoArtistInDB(){
        Album album = new Album();
        album.setAlbumId("al123");
        album.setAlbumName("alName");
        mongoDbServiceClient.addNewAlbumForArtist("C", album);
        cleanUpRepo();

    }

    @Test
    public void testUpdateAlbumForArtist(){
        Artist artist = new Artist();
        artist.setArtistId("dr123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setArtistId("dr123");
        album.setAlbumId("dl123");
        album.setAlbumName("alName");
        albumRepository.save(album);
        Album albumToUpdate = new Album();
        albumToUpdate.setAlbumName("updated");
        albumToUpdate.setAlbumId("dl123");
        albumToUpdate.setArtistId("dr123");
        Album albumUpdated = mongoDbServiceClient.updateExistingAlbum(artist.getArtistId(), albumToUpdate, albumToUpdate.getAlbumId());
        assertEquals(albumUpdated.getAlbumName(), "updated");
        cleanUpRepo();

    }


    @Test(expected = MongoDbException.class)
    public void testUpdateAlbumForArtist_Exception_NoRecordInDB(){
        mongoDbServiceClient.updateExistingAlbum("aa", new Album(), "aa");
        cleanUpRepo();

    }

   @Test
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Ascending() {
     Artist artist = new Artist();
     artist.setArtistId("fr123");
     artist.setArtistName("arName");
     artistRepository.save(artist);
     Album album = new Album();
     album.setArtistId("fr123");
     album.setAlbumId("al123");
     album.setAlbumName("alName1");
     album.setReleaseYear("1990");
     albumRepository.save(album);
     album = new Album();
     album.setArtistId("fr123");
     album.setAlbumId("al123");
     album.setAlbumName("alName");
     album.setReleaseYear("1991");
     albumRepository.save(album);
     List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName(artist.getArtistId(), SortType.ASC);
     assertEquals(albums.size(), 2);
     assertEquals(albums.get(0).getReleaseYear(), "1990");
     assertEquals(albums.get(0).getAlbumName(), "alName1");
     assertEquals(albums.get(1).getReleaseYear(), "1991");
     assertEquals(albums.get(1).getAlbumName(), "alName");

       cleanUpRepo();

 }


    @Test(expected = MongoDbException.class)
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Ascending_Excption_ArtisID_Mismatch() {
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setArtistId("ar123");
        album.setAlbumId("al123");
        album.setAlbumName("alName1");
        album.setReleaseYear("1990");
        albumRepository.save(album);
        artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        album = new Album();
        album.setArtistId("ar123");
        album.setAlbumId("al123");
        album.setAlbumName("alName");
        album.setReleaseYear("1991");
        albumRepository.save(album);
        List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName("gr123", SortType.ASC);

        cleanUpRepo();
    }

    @Test(expected = MongoDbException.class)
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Ascending_Excption_NoRecordInDB() {

        List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName("gr123", SortType.ASC);

        cleanUpRepo();
    }

    @Test
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Descending() {
        Artist artist = new Artist();
        artist.setArtistId("hr123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setArtistId("hr123");
        album.setAlbumId("cl123");
        album.setAlbumName("alName1");
        album.setReleaseYear("1990");
        albumRepository.save(album);
        album = new Album();
        album.setArtistId("hr123");
        album.setAlbumId("cl123");
        album.setAlbumName("alName");
        album.setReleaseYear("1991");
        albumRepository.save(album);
        List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName(artist.getArtistId(), SortType.DESC);
        assertEquals(albums.size(), 2);
        assertEquals(albums.get(0).getReleaseYear(), "1991");
        assertEquals(albums.get(0).getAlbumName(), "alName");
        assertEquals(albums.get(1).getReleaseYear(), "1990");
        assertEquals(albums.get(1).getAlbumName(), "alName1");
        cleanUpRepo();


    }


    @Test(expected = MongoDbException.class)
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Descending_Excption_ArtisID_Mismatch() {
        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setArtistId("ar123");
        album.setAlbumId("al123");
        album.setAlbumName("alName1");
        album.setReleaseYear("1990");
        albumRepository.save(album);
        artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        album = new Album();
        album.setArtistId("ar123");
        album.setAlbumId("al123");
        album.setAlbumName("alName");
        album.setReleaseYear("1991");
        albumRepository.save(album);
        List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName("gr123", SortType.DESC);

        cleanUpRepo();
    }

    @Test(expected = MongoDbException.class)
    public void testFindAllAlbumsForArtistSortByReleaseYearAndAlbumName_Descending_Excption_NoRecordInDB() {

        List<Album> albums = mongoDbServiceClient.findAllAlbumsForArtistSortByReleaseYearAndAlbumName("gr123", SortType.ASC);

        cleanUpRepo();
    }


    @Test
    public void testFindAllAlbumsForArtistFilterByGenre() {
        Artist artist = new Artist();
        artist.setArtistId("hr123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        Album album = new Album();
        album.setArtistId("hr123");
        album.setAlbumId("cl123");
        album.setAlbumName("alName1");
        album.setReleaseYear("1990");
        album.setGenres(Arrays.asList(new String[]{"pop"}));
        albumRepository.save(album);
        album = new Album();
        album.setArtistId("hr123");
        album.setAlbumId("cl123");
        album.setAlbumName("alName");
        album.setReleaseYear("1991");
        album.setGenres(Arrays.asList(new String[]{"rock"}));
        albumRepository.save(album);
        List<Album> albums = mongoDbServiceClient.findAllAbumByGenreRegex(artist.getArtistId(), "pop");
        assertEquals(albums.size(), 1);
        assertEquals(albums.get(0).getReleaseYear(), "1990");
        assertEquals(albums.get(0).getAlbumName(), "alName1");
        cleanUpRepo();


    }

    @Test
    public void testGetAllArtistPaged() {

        Artist artist = new Artist();
        artist.setArtistId("ar123");
        artist.setArtistName("arName");
        artistRepository.save(artist);
        artist = new Artist();
        artist.setArtistId("br123");
        artist.setArtistName("vrName");
        artistRepository.save(artist);
       Page<Artist> artists = mongoDbServiceClient.findAllPage(PageRequest.of(0,1));
       assertEquals(artists.getTotalElements(), 2);
        assertEquals(artists.getTotalPages(), 2);

    }


}
