package com.app.neueda.logic;

/**
 * Interface for URL generator.
 *
 * @see Base62URLGenerator
 */
public interface URLGenerator {

    /**
     * Method to take in unique ID and generate a Short URL
     *
     * @param id Unique identifier to the URL mapping record
     * @return Encoded code to be appended to the baseURL
     */
    String generateShortURL(long id);

    /**
     * Method to decode hashcode to get back ID
     * @param urlCode base62 encoded Id
     * @return Id Unique identifier to the URL mapping record
     */
    Long retrieveURLMappingId(String urlCode);


}
