package com.music.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


/** AlbumsResponse model class
 *Represent model class for API response
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
@ToString

public class AlbumsResponse extends MusicAPIResponse{

    String artistId;
    List<AlbumResponse> albums = new ArrayList<>();
}
