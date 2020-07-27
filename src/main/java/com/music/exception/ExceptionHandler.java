package com.music.exception;

import com.music.model.Error;
import com.music.response.MusicAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/** Exception Handler for the API and also builds the Error
 * for Response Entity
 * @author Proma Chowdhury
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    /**
     * Handles mongodb exception
     * @param backendException
     * @param request
     * @return ResponseEntity
     */

    @org.springframework.web.bind.annotation.ExceptionHandler(MongoDbException.class)
    public ResponseEntity<?> handleBackendException(MongoDbException backendException, HttpServletRequest request) {
        String requestURI = getRequestUriWithParameters(request);
        String loggingMessage = String.format("Mongo database exception at '%s'", requestURI);
        return handleGenericError(backendException, loggingMessage, requestURI);
    }

    /**
     * Handles invalidArtistRequestException
     * @param invalidArtistRequestEcception
     * @param request
     * @return ResponseEntity
     */

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidArtistRequestException.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidArtistRequestException invalidArtistRequestEcception, HttpServletRequest request) {
        String requestURI = getRequestUriWithParameters(request);
        String loggingMessage = String.format("Invalid Request at '%s'", requestURI);
        return handleGenericError(invalidArtistRequestEcception, loggingMessage, requestURI);
    }

    /**
     * Handles jsonOperationException
     * @param jsonOperationException
     * @param request
     * @return ResponseEntity
     */

    @org.springframework.web.bind.annotation.ExceptionHandler(JsonOperationException.class)
    public ResponseEntity<?> handleJsonOperationException(JsonOperationException jsonOperationException, HttpServletRequest request) {
        String requestURI = getRequestUriWithParameters(request);
        String loggingMessage = String.format("Invalid Request at '%s'", requestURI);
        return handleGenericError(jsonOperationException, loggingMessage, requestURI);
    }

    /**
     * Builds a followUp uri
     * @param request
     * @return URI
     */
    private String getRequestUriWithParameters(HttpServletRequest request) {
        String queryParameters = request.getQueryString();
        return request.getRequestURI() + (StringUtils.isBlank(queryParameters) ? "" : "?" + request.getQueryString());
    }

    /**
     * Builds Error for the Response Entity
     * @param exception
     * @param loggingMessage
     * @param requestURI
     * @return ResponseEntity
     */
    private ResponseEntity<?> handleGenericError(GenericException exception, String loggingMessage, String requestURI) {
        log.error(loggingMessage, exception);
        String errorCode = exception.getError().getCode();
        String errorName = exception.getError().getName();
        String errorDescription = exception.getError().getDescription();
        MusicAPIResponse musicAPIResponse = createErrorResponse(new Error(errorCode, errorName, errorDescription, requestURI));
        HttpStatus httpStatus = exception.getError() == null ? HttpStatus.INTERNAL_SERVER_ERROR : exception.getError().getHttpStatus();
        return ResponseEntity.status(httpStatus).body(musicAPIResponse);
    }

    /**
     * Adds Error for the MusicApiResponse
     * @param error
     * @return musicAPIResponse
     */
    private MusicAPIResponse createErrorResponse(Error error) {
        MusicAPIResponse musicAPIResponse = new MusicAPIResponse();
        musicAPIResponse.getErrors().add(error);
        return musicAPIResponse;
    }

}
