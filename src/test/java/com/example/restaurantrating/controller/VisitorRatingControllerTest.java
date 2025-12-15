package com.example.restaurantrating.controller;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restaurantrating.dto.VisitorRatingResponse;
import com.example.restaurantrating.service.VisitorRatingService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(VisitorRatingController.class)
class VisitorRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitorRatingService visitorRatingService;

    @Test
    void getAll_ok() throws Exception {
        when(visitorRatingService.findAll())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/visitorRatings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getById_ok() throws Exception {
        VisitorRatingResponse response = mock(VisitorRatingResponse.class);

        when(visitorRatingService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/visitorRatings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create_ok() throws Exception {
        VisitorRatingResponse response = mock(VisitorRatingResponse.class);

        when(visitorRatingService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/visitorRatings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "visitorId":1,
                                  "restaurantId":2,
                                  "rating":5,
                                  "reviewText":"Nice"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void update_ok() throws Exception {
        VisitorRatingResponse response = mock(VisitorRatingResponse.class);

        when(visitorRatingService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/visitorRatings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "visitorId":1,
                                  "restaurantId":2,
                                  "rating":4,
                                  "reviewText":"Updated"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void delete_ok() throws Exception {
        when(visitorRatingService.remove(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/visitorRatings/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
