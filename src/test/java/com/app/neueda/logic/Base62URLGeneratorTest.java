package com.app.neueda.logic;

import com.app.neueda.logic.Base62URLGenerator;
import com.app.neueda.model.URLMappingRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Base62URLGeneratorTest {

    Base62URLGenerator base62URLGenerator;

    @Before
    public void setup() {
        base62URLGenerator = new Base62URLGenerator();
    }

    @Test
    public void testShortURLGeneration() {
        URLMappingRecord updatedURLMappingRecord = URLMappingRecord.builder()
                .id(1L)
                .originalURL("https://www.google.com/search?q=hello")
                .build();


        String url = base62URLGenerator.generateShortURL(updatedURLMappingRecord.getId());
        String expectedHash = "3ibJF44";
        assertEquals(url, expectedHash, String.format("The url generated '%s' does not match the expected url '%s'.", url, expectedHash));
    }


    @Test(expected = AssertionError.class)
    public void testShortURLGeneration_NullId() {
        URLMappingRecord updatedURLMappingRecord = URLMappingRecord.builder()
                .originalURL("https://www.google.com/search?q=hello")
                .build();

        base62URLGenerator.generateShortURL(updatedURLMappingRecord.getId());
    }

    @Test
    public void testShortURLGeneration_IdGreaterThan10000() {
        URLMappingRecord updatedURLMappingRecord = URLMappingRecord.builder()
                .id(12345L)
                .originalURL("https://www.google.com/search?q=hello")
                .build();

        String url = base62URLGenerator.generateShortURL(updatedURLMappingRecord.getId());
        String expectedHash = "3nKG6iK";
        assertEquals(url, expectedHash, String.format("The url generated '%s' does not match the expected url '%s'.", url, expectedHash));
    }

    @Test
    public void retrieveURLMappingId_NullId() {
        Long id = base62URLGenerator.retrieveURLMappingId(null);
        long expectedId = 12345L;
        assert id == expectedId : String.format("The id %s' does not match the expected id '%d'.", id, expectedId);
    }

    @Test
    public void retrieveURLMappingId_IdGreaterThan10000() {
        String urlCode = "3nKG6iK";
        Long id = base62URLGenerator.retrieveURLMappingId(urlCode);
        long expectedId = 12345L;
        assert id == expectedId : String.format("The id %s' does not match the expected id '%d'.", id, expectedId);
    }
}