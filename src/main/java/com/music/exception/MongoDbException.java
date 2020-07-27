package com.music.exception;

import com.music.model.Error;
import lombok.Getter;


/** MongoDbException Handler for the API
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
public class MongoDbException  extends GenericException {

    private Error error;

    public MongoDbException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public MongoDbException(String message, Exception cause) {
        super(message, cause);
    }
}
