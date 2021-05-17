package com.app.neueda.logic;

import com.app.neueda.logic.URLGenerator;
import io.cucumber.messages.internal.com.google.common.primitives.Longs;
import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Base62 implementation of URLGenerator.
 */
@Component
public class Base62URLGenerator implements URLGenerator {

    // Library for base62 encoding/decoding
    private final Base62 base62;

    /**
     * Default constructor.
     */
    public Base62URLGenerator() {
        base62 = Base62.createInstance();
    }
    @Override
    public String generateShortURL(long id) {

        assert id > 0 : String.format("Invalid id - %o", id);

        String canonicalId = generateCanonicalId(id);

        final byte[] encoded = base62.encode(canonicalId.getBytes(StandardCharsets.UTF_8));
        return new String(encoded);
    }

    @Override
    public Long retrieveURLMappingId(String hashcode) {
        return decodeHashCode(hashcode);
    }

    private Long decodeHashCode(String urlCode) {

        // Assert the url code is not null
        assert urlCode != null : "urlCode is null";

        // Decode the URL code
        byte[] longBytes = base62.decode(urlCode.getBytes(StandardCharsets.UTF_8));

        // Parse the string to long
        String longString = new String(longBytes);
        long id = Long.parseLong(longString);

        return (id - 9999);
    }

    /**
     * Generate Canonical ID.
     * @param id Identifier to URL Mapping Record
     * @return canonical ID to be encoded
     */
    private String generateCanonicalId(long id) {
        return Long.valueOf(id + 9999).toString();
    }
}
