
package com.music.config;

/** Contains all global variable.
 * @author Proma Chowdhury
 * @version 1.0
 */
public interface GlobalVariables {

    public static final String PARAM_ARTIST_ID = "artistId";
    public static final String PARAM_ALBUM_ID = "albumId";
    public static final String INVALID_ARTIST_REQUEST_ERROR_CODE = "MSAPI-001";
    public static final String INVALID_ARTIST_REQUEST_ERROR_NAME = "INVALID_ARTIST_REQUEST";
    public static final String INVALID_ARTIST_REQUEST_ERROR_DESC = "REQUEST HAS NO BODY OR ARTIST ID NOT PRESENT";
    public static final String CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE = "MSAPI-002";
    public static final String CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME = "INVALID_REQUEST_CREATE_ALBUM";
    public static final String CREATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC = "REQUEST HAS NO BODY OR ARTIST ID /ALBUM ID NOT PRESENT";
    public static final String UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE = "MSAPI-003";
    public static final String UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME = "INVALID_REQUEST_UPDATE_ALBUM";
    public static final String UPDATE_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC = "REQUEST HAS NO BODY OR ARTIST ID /ALBUM ID NOT PRESENT";
    public static final String GET_ALBUM_INVALID_ARTIST_REQUEST_ERROR_CODE = "MSAPI-004";
    public static final String GET_ALBUM_INVALID_ARTIST_REQUEST_ERROR_NAME = "INVALID_REQUEST_GET_ALBUM";
    public static final String GET_ALBUM_INVALID_ARTIST_REQUEST_ERROR_DESC = "REQUEST HAS NO ALBUM ID";
    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_CODE = "MSAPI-005";
    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_NAME = "RESOURCE NOT FOUND";
    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ARTIST_ERROR_DESC = "NO ARTIST FOUND IN DB FOR ARTISTID";

    public static final String MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ARTIST_ERROR_CODE = "MSAPI-006";
    public static final String MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ERROR_NAME = "RESOURCE NOT FOUND";
    public static final String MONGO_NO_RESOURCE_FOUND_CREATE_ALBUM_FOR_ARTIST_ERROR_DESC = "NO ARTIST FOUND IN DB FOR ARTISTID";

    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ARTIST_ERROR_CODE = "MSAPI-007";
    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ERROR_NAME = "RESOURCE NOT FOUND";
    public static final String MONGO_NO_RESOURCE_FOUND_UPDATE_ALBUM_FOR_ARTIST_ERROR_DESC = "NO ARTIST FOUND IN DB FOR ARTISTID";


    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_CODE = "MSAPI-008";
    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_NAME = "RESOURCE NOT FOUND";
    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_FOR_ARTIST_ERROR_DESC = "NO ARTIST FOUND IN DB FOR ARTISTID";

    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_CODE = "MSAPI-009";
    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_NAME = "RESOURCE NOT FOUND";
    public static final String MONGO_NO_RESOURCE_FOUND_FIND_ALBUM_SORTED_FOR_ARTIST_ERROR_DESC = "NO ARTIST FOUND IN DB FOR ARTISTID";


    public static final String JSON_OPERATION_ERROR_CODE = "MSAPI-010";
    public static final String JSON_OPERATION_ERROR_NAME = "CANNOT READ JSON";
    public static final String JSON_OPERATION_ERROR_DESC = "CANNOT READ JSON PROPERTY";
}
