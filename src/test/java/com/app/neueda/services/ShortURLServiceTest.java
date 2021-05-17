package com.app.neueda.services;

import com.app.neueda.exceptions.ApplicationException;
import com.app.neueda.logic.Base62URLGenerator;
import com.app.neueda.logic.URLGenerator;
import com.app.neueda.model.URLMappingRecord;
import com.app.neueda.repository.ShortURLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
class ShortURLServiceTest {

    ShortURLRepository repository;

    URLGenerator urlGenerator;

    @BeforeEach
    public void setup() {

        repository = Mockito.mock(ShortURLRepository.class);
        urlGenerator = new Base62URLGenerator();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void save() {

        String originalURL = "www.google.com";
        String expectedURL = "http://localhost/3ibJF44";

        URLMappingRecord mappingRecord = URLMappingRecord
                .builder()
                .originalURL(originalURL)
                .build();


        URLMappingRecord savedMappingRecord = URLMappingRecord
                .builder()
                .id(1L)
                .originalURL(originalURL)
                .shortURL(expectedURL)
                .build();

        Mockito.when(repository.save(mappingRecord)).thenReturn(savedMappingRecord);

        ShortURLService shortURLService = new ShortURLService(urlGenerator, repository);
        URLMappingRecord verifiedMappingRecord = shortURLService.save(mappingRecord);

        assertEquals(expectedURL, verifiedMappingRecord.getShortURL() );

    }

    @Test
    void getMappedURL() {
        ShortURLService shortURLService = new ShortURLService(urlGenerator, repository);
        try {
            String originalURL = "www.google.com";
            String shortURL = "http://localhost/3ibJF44";

            URLMappingRecord savedMappingRecord = URLMappingRecord
                    .builder()
                    .id(1L)
                    .originalURL(originalURL)
                    .shortURL(shortURL)
                    .build();

            Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(savedMappingRecord));

           String actualURL = shortURLService.getMappedURL("3ibJF44");
           assertEquals(originalURL, actualURL, String.format("The actual URL: %s does not match the expected URL: %s", actualURL, originalURL));
        } catch (ApplicationException e) {
            assertNull(e);
        }
    }
}