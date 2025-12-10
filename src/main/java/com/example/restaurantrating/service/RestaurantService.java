package com.example.restaurantrating.service;

import com.example.restaurantrating.domain.entity.Restaurant;
import com.example.restaurantrating.mapper.RestaurantMapper;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.dto.RestaurantRequest;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantResponse create(RestaurantRequest dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    public RestaurantResponse update(Long id, RestaurantRequest dto) {
        Restaurant existing = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден"));

        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setCuisineType(dto.cuisineType());
        existing.setAverageBill(dto.averageBill());

        existing = restaurantRepository.save(existing);

        return restaurantMapper.toDto(existing);
    }

    public boolean remove(Long id) {
        if (!restaurantRepository.existsById(id))
            return false;

        restaurantRepository.deleteById(id);
        return true;
    }


    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден"));

        return restaurantMapper.toDto(restaurant);
    }

    public void updateAverageRating(Long restaurantId, BigDecimal avg) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден"));

        restaurant.setUserRating(avg);
        restaurantRepository.save(restaurant);
    }
}
