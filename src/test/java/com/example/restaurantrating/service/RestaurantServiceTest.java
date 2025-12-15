package com.example.restaurantrating.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restaurantrating.domain.KitchenType;
import com.example.restaurantrating.domain.entity.Restaurant;
import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.mapper.RestaurantMapper;
import com.example.restaurantrating.repository.RestaurantRepository;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void create_success() {
        RestaurantRequest request = new RestaurantRequest(
                "Test", "Text", KitchenType.ЕВРОПЕЙСКАЯ, BigDecimal.valueOf(1000)
        );

        Restaurant entity = new Restaurant();
        Restaurant saved = new Restaurant();
        RestaurantResponse response = new RestaurantResponse(1L, "Test", "Text", KitchenType.ЕВРОПЕЙСКАЯ, BigDecimal.valueOf(1000), null);

        when(restaurantMapper.toEntity(request)).thenReturn(entity);
        when(restaurantRepository.save(entity)).thenReturn(saved);
        when(restaurantMapper.toDto(saved)).thenReturn(response);

        RestaurantResponse result = restaurantService.create(request);

        assertEquals("Test", result.name());
        verify(restaurantRepository).save(entity);
    }

    @Test
    void findById_success() {
        Restaurant restaurant = new Restaurant();
        RestaurantResponse response = new RestaurantResponse(
                1L, "Test", "Text",
                KitchenType.ЕВРОПЕЙСКАЯ, BigDecimal.valueOf(1000), null
        );

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toDto(restaurant))
                .thenReturn(response);

        RestaurantResponse result = restaurantService.findById(1L);

        assertEquals(1L, result.id());
    }

    @Test
    void findById_notFound() {
        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.findById(1L));
    }

    @Test
    void findAll_success() {
        Restaurant r1 = new Restaurant();
        Restaurant r2 = new Restaurant();

        when(restaurantRepository.findAll())
                .thenReturn(List.of(r1, r2));

        when(restaurantMapper.toDto(any(Restaurant.class)))
                .thenReturn(new RestaurantResponse(
                        1L, "Test", "Text",
                        KitchenType.ЕВРОПЕЙСКАЯ, BigDecimal.valueOf(1000), null
                ));

        List<RestaurantResponse> result = restaurantService.findAll();

        assertEquals(2, result.size());
    }

     @Test
    void update_success() {
        RestaurantRequest request = new RestaurantRequest(
                "New", "NewText", KitchenType.ИСПАНСКАЯ, BigDecimal.valueOf(2000)
        );

        Restaurant existing = new Restaurant();
        Restaurant updated = new Restaurant();

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(restaurantRepository.save(existing))
                .thenReturn(updated);
        when(restaurantMapper.toDto(updated))
                .thenReturn(new RestaurantResponse(
                        1L, "New", "NewText",
                        KitchenType.ИСПАНСКАЯ, BigDecimal.valueOf(2000), null
                ));

        RestaurantResponse result = restaurantService.update(1L, request);

        assertEquals("New", result.name());
        verify(restaurantRepository).save(existing);
    }

    @Test
    void update_notFound() {
        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(1L, mock(RestaurantRequest.class)));
    }
    
    @Test
    void remove_success() {
        when(restaurantRepository.existsById(1L))
                .thenReturn(true);

        boolean result = restaurantService.remove(1L);

        assertTrue(result);
        verify(restaurantRepository).deleteById(1L);
    }

    @Test
    void remove_notFound() {
        when(restaurantRepository.existsById(1L))
                .thenReturn(false);

        boolean result = restaurantService.remove(1L);

        assertFalse(result);
        verify(restaurantRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateAverageRating_success() {
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        restaurantService.updateAverageRating(1L, BigDecimal.valueOf(4.5));

        assertEquals(BigDecimal.valueOf(4.5), restaurant.getUserRating());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void updateAverageRating_notFound() {
        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.updateAverageRating(1L, BigDecimal.ONE));
    }
}
