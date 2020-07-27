package com.music.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.music.model.Error;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** ErrorResponse model class
 *Represent model class for API response errors
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        if (error != null && !errors.stream().anyMatch(e -> e.getCode().equals(error.getCode()))) {
            errors.add(error);
        }
    }

    public Error getError(String errorCode) {
        return errors.stream()
                .filter(error -> error.getCode().equals(errorCode))
                .findFirst()
                .orElse(null);
    }
}
