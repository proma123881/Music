package com.music.exception;

import com.music.model.Error;
import lombok.Getter;

import javax.lang.model.type.ErrorType;

/** GenericException Handler for the API
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
public class GenericException extends RuntimeException {

    private Error error;

    public GenericException(String description) {
        super(description);
    }

    public GenericException(String description, Exception cause) {
        super(description, cause);
    }

    public GenericException(Error error) {
        this.error = error;

    }


}
