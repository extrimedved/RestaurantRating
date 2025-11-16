package com.example.restaurantrating.service;

import com.example.restaurantrating.domain.entity.Restaurant;
import com.example.restaurantrating.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getId() == null) 
            throw new IllegalArgumentException("ID обязателен");
        if (restaurant.getName() == null) 
            throw new IllegalArgumentException("Имя ресторана обязательно");
        if (restaurant.getCuisineType() == null) 
            throw new IllegalArgumentException("Тип кухни обязателен");
        if (restaurant.getAverageBill() == null || restaurant.getAverageBill().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Средний чек обязателен и должен быть > 0");

        return restaurantRepository.save(restaurant);
    }

     public boolean remove(Long id) {
        if(id == null)
            throw new IllegalArgumentException("Введите id ресторана для удаления");
        return restaurantRepository.remove(id);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Введите id ресторана для поиска по id");
        }
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant == null) {
            throw new RuntimeException("Ресторан не найден");
        }
        return restaurant;
    }

    public void updateAverageRating(Long restaurantId, BigDecimal average) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        restaurant.setUserRating(average);
        restaurantRepository.save(restaurant);
    }
}
