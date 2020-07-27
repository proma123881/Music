package com.music.mapper;


import com.music.MusicApplication;
import com.music.model.Album;
import com.music.response.AlbumResponse;
import com.music.response.TracksResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MusicApplication.class })
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class DiscogsAPIMapperTest {

    @InjectMocks
    DiscogsAPIMapper discogsAPIMapper;

    @Test
    public void testBuildTrackResponse() {

        TracksResponse tracksResponse = new TracksResponse();
        HashMap<String,String> trackDetails = new  HashMap<String,String> ();
        trackDetails.put("1", "track1");
        trackDetails.put("2", "track2");
        discogsAPIMapper.buildTrackResponse(trackDetails, tracksResponse);
        assertEquals(tracksResponse.getTracks().size(), 2);
        assertEquals(tracksResponse.getTracks().get(0).getTrackTitle(), "track1");
        assertEquals(tracksResponse.getTracks().get(0).getTrackNumber(), "1");
        assertEquals(tracksResponse.getTracks().get(1).getTrackTitle(), "track2");
        assertEquals(tracksResponse.getTracks().get(1).getTrackNumber(), "2");
    }

    @Test
    public void testbuildAlbumResponse() {

        HashMap<String,String> trackDetails = new  HashMap<String,String> ();
        Album album = new Album();
        AlbumResponse albumResponse = new AlbumResponse();
        trackDetails.put("1", "track1");
        trackDetails.put("2", "track2");
        discogsAPIMapper.buildAlbumResponse(album, trackDetails, albumResponse);
        assertEquals(albumResponse.getTracks().size(), 2);
        assertEquals(albumResponse.getTracks().get(0).getTrackTitle(), "track1");
        assertEquals(albumResponse.getTracks().get(0).getTrackNumber(), "1");
        assertEquals(albumResponse.getTracks().get(1).getTrackTitle(), "track2");
        assertEquals(albumResponse.getTracks().get(1).getTrackNumber(), "2");

    }


}
