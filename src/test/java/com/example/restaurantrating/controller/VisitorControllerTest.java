package com.example.restaurantrating.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restaurantrating.domain.GenderType;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.service.VisitorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitorService visitorService;

    @Test
    void getById_ok() throws Exception {
        when(visitorService.findById(1L))
                .thenReturn(new VisitorResponse(
                        1L, "Alex", 25, GenderType.МУЖЧИНА
                ));

        mockMvc.perform(get("/api/visitor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"));
    }

    @Test
    void getAll_ok() throws Exception {
        when(visitorService.findAll())
                .thenReturn(List.of(
                        new VisitorResponse(1L, "Alex", 25, GenderType.МУЖЧИНА)
                ));

        mockMvc.perform(get("/api/visitor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void create_ok() throws Exception {
        when(visitorService.create(any()))
                .thenReturn(new VisitorResponse(
                        1L, "Alex", 25, GenderType.МУЖЧИНА
                ));

        mockMvc.perform(post("/api/visitor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"Alex",
                                  "age":25,
                                  "gender":"МУЖЧИНА"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_ok() throws Exception {
        when(visitorService.update(eq(1L), any()))
                .thenReturn(new VisitorResponse(
                        1L, "Bob", 30, GenderType.МУЖЧИНА
                ));

        mockMvc.perform(put("/api/visitor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"Bob",
                                  "age":30,
                                  "gender":"МУЖЧИНА"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"));
    }

    @Test
    void delete_ok() throws Exception {
        when(visitorService.remove(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/visitor/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
