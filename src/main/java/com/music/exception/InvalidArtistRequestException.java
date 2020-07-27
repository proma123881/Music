package com.music.exception;


import com.music.model.Error;
import lombok.Getter;


/** InvalidArtistRequestException Handler for the API
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
public class InvalidArtistRequestException  extends GenericException {

    String message;
    public InvalidArtistRequestException(Error error, String message) {
        super(error);
        this.message = message;

    }
}
