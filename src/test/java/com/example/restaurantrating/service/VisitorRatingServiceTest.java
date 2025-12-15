package com.example.restaurantrating.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restaurantrating.domain.entity.Restaurant;
import com.example.restaurantrating.domain.entity.VisitorRating;
import com.example.restaurantrating.dto.VisitorRatingRequest;
import com.example.restaurantrating.dto.VisitorRatingResponse;
import com.example.restaurantrating.mapper.VisitorRatingMapper;
import com.example.restaurantrating.repository.VisitorRatingRepository;

@ExtendWith(MockitoExtension.class)
class VisitorRatingServiceTest {

    @Mock
    private VisitorRatingRepository visitorRatingRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private VisitorService visitorService;

    @Mock
    private VisitorRatingMapper visitorRatingMapper;

    @InjectMocks
    private VisitorRatingService visitorRatingService;

    @Test
    void create_success_recalcCalled() {
        VisitorRatingRequest request =
                new VisitorRatingRequest(1L, 2L, 5, "Nice");

        VisitorRating entity = new VisitorRating();
        entity.setRating(5);

        VisitorRating saved = new VisitorRating();

        when(visitorService.findById(1L)).thenReturn(null);
        when(restaurantService.findById(2L)).thenReturn(null);
        when(visitorRatingMapper.toEntity(request)).thenReturn(entity);
        when(visitorRatingRepository.save(entity)).thenReturn(saved);
        when(visitorRatingRepository.findByRestaurantId(2L))
                .thenReturn(List.of(saved));
        when(visitorRatingMapper.toDto(saved))
                .thenReturn(mock(VisitorRatingResponse.class));

        VisitorRatingResponse result =
                visitorRatingService.create(request);

        assertNotNull(result);
        verify(restaurantService)
                .updateAverageRating(eq(2L), any(BigDecimal.class));
    }

    @Test
    void create_emptyRatings_setsZero() {
        VisitorRatingRequest request =
                new VisitorRatingRequest(1L, 2L, 5, "Nice");

        VisitorRating entity = new VisitorRating();

        when(visitorService.findById(1L)).thenReturn(null);
        when(restaurantService.findById(2L)).thenReturn(null);
        when(visitorRatingMapper.toEntity(request)).thenReturn(entity);
        when(visitorRatingRepository.save(entity)).thenReturn(entity);
        when(visitorRatingRepository.findByRestaurantId(2L))
                .thenReturn(List.of());

        visitorRatingService.create(request);

        verify(restaurantService)
                .updateAverageRating(2L, BigDecimal.ZERO);
    }

    @Test
    void update_success_recalcCalled() {
        VisitorRating existing = new VisitorRating();
        existing.setRating(3);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        existing.setRestaurant(restaurant);

        when(visitorRatingRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(visitorRatingRepository.save(existing))
                .thenReturn(existing);
        when(visitorRatingRepository.findByRestaurantId(2L))
                .thenReturn(List.of(existing));
        when(visitorRatingMapper.toDto(existing))
                .thenReturn(mock(VisitorRatingResponse.class));

        VisitorRatingRequest request =
                new VisitorRatingRequest(1L, 2L, 4, "Updated");

        VisitorRatingResponse result =
                visitorRatingService.update(1L, request);

        assertNotNull(result);
        verify(restaurantService)
                .updateAverageRating(eq(2L), any(BigDecimal.class));
    }

    @Test
    void update_notFound() {
        when(visitorRatingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> visitorRatingService.update(
                        1L, mock(VisitorRatingRequest.class)
                ));
    }

    @Test
    void remove_success_recalcCalled() {
        VisitorRating rating = new VisitorRating();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        rating.setRestaurant(restaurant);

        when(visitorRatingRepository.existsById(1L))
                .thenReturn(true);
        when(visitorRatingRepository.findById(1L))
                .thenReturn(Optional.of(rating));
        when(visitorRatingRepository.findByRestaurantId(2L))
                .thenReturn(List.of());

        boolean result = visitorRatingService.remove(1L);

        assertTrue(result);
        verify(visitorRatingRepository).deleteById(1L);
        verify(restaurantService)
                .updateAverageRating(2L, BigDecimal.ZERO);
    }

    @Test
    void remove_notFound() {
        when(visitorRatingRepository.existsById(1L))
                .thenReturn(false);

        boolean result = visitorRatingService.remove(1L);

        assertFalse(result);
        verify(visitorRatingRepository, never()).deleteById(anyLong());
    }

    @Test
    void findById_success() {
        VisitorRating rating = new VisitorRating();

        when(visitorRatingRepository.findById(1L))
                .thenReturn(Optional.of(rating));
        when(visitorRatingMapper.toDto(rating))
                .thenReturn(mock(VisitorRatingResponse.class));

        VisitorRatingResponse result =
                visitorRatingService.findById(1L);

        assertNotNull(result);
    }

    @Test
    void findById_notFound() {
        when(visitorRatingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> visitorRatingService.findById(1L));
    }

    @Test
    void findAll_success() {
        when(visitorRatingRepository.findAll())
                .thenReturn(List.of(new VisitorRating(), new VisitorRating()));
        when(visitorRatingMapper.toDto(any(VisitorRating.class)))
                .thenReturn(mock(VisitorRatingResponse.class));

        List<VisitorRatingResponse> result =
                visitorRatingService.findAll();

        assertEquals(2, result.size());
    }
}