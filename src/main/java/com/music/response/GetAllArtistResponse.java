package com.music.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/** GetAllArtistResponse model class
 *Represent model class for API response errors
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
public class GetAllArtistResponse extends MusicAPIResponse{

    List<ArtistResponse> artistResponses = new ArrayList<>();
}
