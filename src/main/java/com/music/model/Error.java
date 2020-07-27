package com.music.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.http.HttpStatus;

/** Error model class
 * Contains Error details for ResponseEntity
 * @author Proma Chowdhury
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Error {



    public Error(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }


    public Error(String code, String name, String description, HttpStatus httpStatus) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public Error(String code, String name, String description, String followupURI) {
        this(code, name, description);
        this.name = name;
        this.followupURI = followupURI;
    }



    private String code;


    @ToStringExclude
    private String name;

    private String description;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String followupURI;

}

