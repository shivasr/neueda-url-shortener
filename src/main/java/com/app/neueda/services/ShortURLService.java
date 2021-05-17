package com.app.neueda.services;

import com.app.neueda.exceptions.ApplicationException;
import com.app.neueda.logic.URLGenerator;
import com.app.neueda.model.URLMappingRecord;
import com.app.neueda.repository.ShortURLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ShortURLService {

    Logger logger = LoggerFactory.getLogger(ShortURLService.class);

    // JPA repository to create/read/update/delete url data
    private final ShortURLRepository repository;

    // URL generator
    private final URLGenerator urlGenerator;

    /**
     * Constructor
     *
     * @param urlGenerator Implementation of URL generator interface
     * @param repository Repository to the URLMappingRecord
     */
    public ShortURLService(URLGenerator urlGenerator, ShortURLRepository repository) {
        this.urlGenerator = urlGenerator;
        this.repository = repository;
    }

    /**
     * This is the service method to generate short URLs.
     *
     * @param URLMappingRecord ShortURL object containing the actual URL to map.
     * @return ShortURL object containing the generated short URL.
     */
    public URLMappingRecord save(URLMappingRecord URLMappingRecord) {
        // Create a mapping record for the give URL and fetch the ID.
        URLMappingRecord updatedURLMappingRecord = repository.save(URLMappingRecord);

        // Generate short URL using Base62 encoding method.
        String generatedShortURL = urlGenerator.generateShortURL(updatedURLMappingRecord.getId());

        // Set the generatedURL
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        URLMappingRecord.setShortURL(new StringBuffer(baseUrl).append("/").append(generatedShortURL).toString());
        logger.debug(String.format("Mapped URL: %s", URLMappingRecord.getShortURL()));
        return repository.save(URLMappingRecord);
    }

    /**
     * Method to get mapped URL required to redirect when the user request for.
     *
     * @param urlCode URL code to lookup for mapped original URL.
     *
     * @return Mapped long URL.
     *
     * @throws ApplicationException when he urk code is invalid.
     */
    public String getMappedURL(String urlCode) throws ApplicationException {
        logger.debug(String.format("Got mapping request for the code: %s", urlCode));

        // Retrieve the ID from the URL code
        Long id = urlGenerator.retrieveURLMappingId(urlCode);

        // Get the URL Mapping record
        URLMappingRecord URLMappingRecord = repository.findById(id).orElseThrow(() -> new ApplicationException(String.format("Unknown short URL: %s", urlCode)));

        // Return the original URL for redirection.
        logger.debug(String.format(String.format("Got mapped original URL: %s", URLMappingRecord.getOriginalURL())));
        return URLMappingRecord.getOriginalURL();
    }
}
