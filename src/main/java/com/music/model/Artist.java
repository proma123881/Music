package com.music.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


/** Artist model class for mongo db
 * stores artist details
 * @author Proma Chowdhury
 * @version 1.0
 */
@Data
@Setter
@Getter
@ToString
public class Artist  {

    @Id
    @JsonIgnore
    private ObjectId objectId;
    @Parsed
    private String artistId;
    @Parsed
    private String artistName;


}
