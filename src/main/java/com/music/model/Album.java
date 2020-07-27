package com.music.model;


import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/** ALbum model class for mongo db
 * @author Proma Chowdhury
 * @version 1.0
 */
@Data
@Getter
@Setter
public class Album {

    @Parsed
    String albumId;
    @Parsed
    String albumName;
    @Parsed
    List<String> genres = new ArrayList<>();
    @Parsed
    String releaseYear;
    @Parsed
    String artistId;

}
