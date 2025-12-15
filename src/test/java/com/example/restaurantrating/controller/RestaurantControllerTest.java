package com.example.restaurantrating.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restaurantrating.domain.KitchenType;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.service.RestaurantService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    void getById_ok() throws Exception {
        when(restaurantService.findById(1L))
                .thenReturn(new RestaurantResponse(
                        1L, "Test", "Text",
                        KitchenType.ЕВРОПЕЙСКАЯ,
                        BigDecimal.valueOf(1000), null
                ));

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void getAll_ok() throws Exception {
        when(restaurantService.findAll())
                .thenReturn(List.of(
                        new RestaurantResponse(1L, "Alexandr", "Text",
                                KitchenType.ЕВРОПЕЙСКАЯ,
                                BigDecimal.valueOf(1000), null)
                ));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void create_ok() throws Exception {
        when(restaurantService.create(any()))
                .thenReturn(new RestaurantResponse(
                        1L, "Test", "Text",
                        KitchenType.ЕВРОПЕЙСКАЯ,
                        BigDecimal.valueOf(1000), null
                ));

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"Test",
                                  "description":"Text",
                                  "cuisineType":"ЕВРОПЕЙСКАЯ",
                                  "averageBill":1000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_ok() throws Exception {
        when(restaurantService.update(eq(1L), any()))
                .thenReturn(new RestaurantResponse(
                        1L, "New", "NewText",
                        KitchenType.ИСПАНСКАЯ,
                        BigDecimal.valueOf(2000), null
                ));

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"New",
                                  "description":"NewText",
                                  "cuisineType":"ИСПАНСКАЯ",
                                  "averageBill":2000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    void delete_ok() throws Exception {
        when(restaurantService.remove(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

