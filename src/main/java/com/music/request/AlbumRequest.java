package com.music.request;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/** AlbumRequest model class
 *Represent model class for API request
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
public class AlbumRequest {
        String albumId;

        String albumName;

        List<String> genre;

        String releaseYear;
    }

