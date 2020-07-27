package com.music.model;


/** Enum representing the possible
 * values for a SORT parameter
 * @author Proma Chowdhury
 * @version 1.0
 */
public enum SortType {

    ASC,
    DESC;

    public static SortType fromString(String key) {
        for (SortType type : SortType.values()) {
            if(type.name().equalsIgnoreCase(key)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value for SortType parameter");
    }
}
