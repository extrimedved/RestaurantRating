package com.example.restaurantrating.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.restaurantrating.domain.entity.Restaurant;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant save(Restaurant restaurant) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getId().equals(restaurant.getId())) {
                restaurants.remove(i);
                break;
            }
        }
        restaurants.add(restaurant);
        return restaurant;
    }

    public boolean remove(Long id) {
        return restaurants.removeIf(v -> v.getId().equals(id));
    }

    public List<Restaurant> findAll() {
        return Collections.unmodifiableList(restaurants);
    }

    public Restaurant findById(Long id) {
        return restaurants.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }
}
