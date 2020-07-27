package com.music.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;
/** ArtistResponse model class
 *Represent model class for API response
 * @author Proma Chowdhury
 * @version 1.0
 */
@Setter
@Getter
@ToString
public class ArtistResponse extends MusicAPIResponse {


    private String artistId;

    private String artistName;

    private URI  albumsURI;


}
