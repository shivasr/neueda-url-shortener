package com.app.neueda.controllers;

import com.app.neueda.exceptions.ApplicationException;
import com.app.neueda.model.RequestURL;
import com.app.neueda.model.URLMappingRecord;
import com.app.neueda.services.ShortURLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * REST API controller to host web services to create a short URL and also redirect short URL to original advertised
 * location.
 */
@RestController
public class ShortURLController {

    Logger logger = LoggerFactory.getLogger(ShortURLController.class);

    // CRUD server to manager ShortURL
    private final ShortURLService shortURLService;

    /**
     * Constructor
     *
     * @param shortURLService Service to generate short URL and fetch mapping original URL.
     */
    public ShortURLController(ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }

    // POST /
    @PostMapping("/")
    public @ResponseBody
    URLMappingRecord generateURL(@RequestBody RequestURL requestBody) {
        logger.debug(String.format("Got Generate URL request for original URL: %s", requestBody.getUrl()));

        // Create a new short URL object for persistence
        URLMappingRecord newURLMappingRecord = URLMappingRecord.builder()
                .originalURL(requestBody.getUrl())
                .build();

        // Saved object containing short URL mapped to the URL in the request.
        newURLMappingRecord = shortURLService.save(newURLMappingRecord);

        logger.debug(String.format("Returning the short URL: %s", newURLMappingRecord.getShortURL()));
        return newURLMappingRecord;
    }

    // GET /<hashcode>
    @GetMapping("{urlCode}")
    public ModelAndView redirectToMappedURL(@PathVariable("urlCode") String urlCode) {

        String url = "/";
        try {
            url = shortURLService.getMappedURL(urlCode);
            return new ModelAndView("redirect:" + url);
        } catch (ApplicationException e) {
            logger.error(String.format("Error occurred while looking up for encoded URL: %s", urlCode));
        }

        return new ModelAndView(url);
    }
}
