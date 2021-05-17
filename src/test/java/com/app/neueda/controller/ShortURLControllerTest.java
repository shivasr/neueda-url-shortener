package com.app.neueda.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortURLControllerTest {
    public static final String SAMPLE_URL_CODE = "3ibJF44";
    public static final String SAMPLE_URL_CODE_ERROR = "3ibJF446";

    public static final String EXPECTED_URL = "http://localhost/3ibJF44";
    public static final String TEST_ORIGINAL_URL = "https://www.google.com/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createRestAPIShouldReturnShortURL() throws Exception {
        // assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/", new RequestURL(), URLMappingRecord.class)
        //        .getBody().getShortURL().equals("http://localhost:5000/3ibJF44"));

        String content = "{\n" +
                "    \"url\": \"" + TEST_ORIGINAL_URL + "\"" +
                "}";

        this.mockMvc.perform(post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortURL").value(EXPECTED_URL));

    }

    @Test
    public void createRestAPIShouldReturnError() throws Exception {
        // assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/", new RequestURL(), URLMappingRecord.class)
        //        .getBody().getShortURL().equals("http://localhost:5000/3ibJF44"));

        String content = "{\n" +
                "    \"url\": \"" + "\"" +
                "}";
        this.mockMvc.perform(post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getRestAPIShouldReturnSuccess() throws Exception {

        this.mockMvc.perform(get("/" + SAMPLE_URL_CODE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

    }

    @Test
    public void getRestAPIShouldReturnError() throws Exception {

        this.mockMvc.perform(get("/" + SAMPLE_URL_CODE_ERROR)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
