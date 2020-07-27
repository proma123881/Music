package com.music.response;


import lombok.Getter;
import lombok.Setter;
import com.music.model.Track;

import java.util.ArrayList;
import java.util.List;


/** TracksResponse model class
 *Represent response class containing
 * track details for a album
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
public class TracksResponse extends MusicAPIResponse {

    List<Track> tracks = new ArrayList<>();
}
