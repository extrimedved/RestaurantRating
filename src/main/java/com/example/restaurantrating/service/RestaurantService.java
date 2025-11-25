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
        restaurantRepository.create(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    public RestaurantResponse update(Long id, RestaurantRequest dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant = new Restaurant(id, restaurant.getName(), restaurant.getDescription(),
                restaurant.getCuisineType(), restaurant.getAverageBill(), restaurant.getUserRating());
        restaurantRepository.update(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

     public boolean remove(Long id) {
        return restaurantRepository.remove(id);
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        return restaurantMapper.toDto(restaurant);
    }

    public void updateAverageRating(Long restaurantId, BigDecimal average) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        restaurant.setUserRating(average);
        restaurantRepository.update(restaurant);
    }
}
