package com.music.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/** ALbums model class for mongo db
 * stores list of albums for a particular artist
 * @author Proma Chowdhury
 * @version 1.0
 */
@Data
@Setter
@Getter
@ToString
public class Albums {

    @Id
    @JsonIgnore
    private ObjectId objectId;
    @Parsed
    private String artistId;

    private List<Album> albums = new ArrayList<>();

}
