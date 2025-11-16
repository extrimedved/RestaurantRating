package com.example.restaurantrating.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurantrating.domain.entity.VisitorRating;
import com.example.restaurantrating.repository.VisitorRatingRepository;
import com.example.restaurantrating.repository.VisitorRepository;
import com.example.restaurantrating.repository.RestaurantRepository;

@Service
public class VisitorRatingService {
    private final VisitorRatingRepository visitorRatingRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final VisitorRepository visitorRepository;

    public VisitorRatingService(VisitorRatingRepository visitorRatingRepository, RestaurantService restaurantService, 
    VisitorRepository visitorRepository, RestaurantRepository restaurantRepository) {
        this.visitorRatingRepository = visitorRatingRepository;
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
        this.visitorRepository = visitorRepository;

    }

    public VisitorRating save(VisitorRating visitorRating) {
        if (visitorRating.getRating() < 1 || visitorRating.getRating() > 5)
            throw new IllegalArgumentException("Оценка должна быть от 1 до 5");
        if (visitorRating.getRestaurantId() == null || visitorRating.getRestaurantId() <= 0) 
            throw new IllegalArgumentException("ID ресторана обязательно и не должно быть меньше или равно 0");
        if (visitorRating.getVisitorId() == null || visitorRating.getVisitorId() <= 0) 
            throw new IllegalArgumentException("ID посетителя обязательно и не должно быть меньше или равно 0");
        if (restaurantRepository.findById(visitorRating.getRestaurantId()) == null)
            throw new IllegalArgumentException("Ресторан с таким ID не найден");
        if (visitorRepository.findById(visitorRating.getVisitorId()) == null)
            throw new IllegalArgumentException("Посетитель с таким ID не найден");

        visitorRatingRepository.save(visitorRating);
        recalcRatingForRestaurant(visitorRating.getRestaurantId());
        return visitorRating;
    }

    public boolean remove(Long visitorId, Long restaurantId) {
        if(visitorId == null || restaurantId == null){
            throw new IllegalArgumentException("Введите id ресторана и id пользователя");
        }
        boolean removed = visitorRatingRepository.remove(visitorId, restaurantId);
        recalcRatingForRestaurant(restaurantId);
        return removed;
    }

    public List<VisitorRating> findAll() {
        return visitorRatingRepository.findAll();
    }

    public VisitorRating findById(Long visitorId, Long restaurantId) {
        if(visitorId == null || restaurantId == null){
            throw new IllegalArgumentException("Введите id ресторана и id пользователя");
        }
        VisitorRating visitorRating = visitorRatingRepository.findById(visitorId, restaurantId);
        if (visitorRating == null) {
            throw new RuntimeException("Оценка не найдена");
        }
        return visitorRating;
    }

    private void recalcRatingForRestaurant(Long restaurantId) {
        List<VisitorRating> visitorRatings = visitorRatingRepository.findByRestaurantId(restaurantId);
        if (visitorRatings.isEmpty()) {
            restaurantService.updateAverageRating(restaurantId, BigDecimal.ZERO);
            return;
        }

        double avg = visitorRatings.stream()
                .mapToInt(VisitorRating::getRating)
                .average()
                .orElse(0);

        restaurantService.updateAverageRating(restaurantId, BigDecimal.valueOf(avg));
    }
}
