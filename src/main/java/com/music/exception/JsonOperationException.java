package com.music.exception;

import com.music.model.Error;
import lombok.Getter;


    /** InvalidArtistRequestException Handler for the API
     * @author Proma Chowdhury
     * @version 1.0
     */
    @Getter
    public class JsonOperationException  extends GenericException {

        String message;
        public JsonOperationException(Error error, String message) {
            super(error);
            this.message = message;

        }
}
