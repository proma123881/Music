package com.music.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.music.model.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** AlbumResponse model class
 *Represent model class for API response
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
public class AlbumResponse  extends MusicAPIResponse{

    String albumId;

    String albumName;

    List<String> genres = new ArrayList<>();

    String releaseYear;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<Track> tracks = new ArrayList<>();
}
