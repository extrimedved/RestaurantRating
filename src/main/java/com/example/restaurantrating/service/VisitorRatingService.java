package com.example.restaurantrating.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurantrating.domain.entity.VisitorRating;
import com.example.restaurantrating.dto.VisitorRatingRequest;
import com.example.restaurantrating.dto.VisitorRatingResponse;
import com.example.restaurantrating.mapper.VisitorRatingMapper;
import com.example.restaurantrating.repository.VisitorRatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitorRatingService {
    private final VisitorRatingRepository visitorRatingRepository;
    private final RestaurantService restaurantService;
    private final VisitorService visitorService;
    private final VisitorRatingMapper visitorRatingMapper;

    public VisitorRatingResponse create(VisitorRatingRequest dto) {
        if (restaurantService.findById(dto.restaurantId()) == null) 
            throw new IllegalArgumentException("Ресторан с таким ID не найден");
        if (visitorService.findById(dto.visitorId()) == null) 
            throw new IllegalArgumentException("Посетитель с таким ID не найден");
        VisitorRating entity = visitorRatingMapper.toEntity(dto);
        visitorRatingRepository.create(entity);
        recalcRatingForRestaurant(dto.restaurantId());
        return visitorRatingMapper.toDto(entity);
    }

    public VisitorRatingResponse update(Long visitorId, Long restaurantId, VisitorRatingRequest dto) {
        if (restaurantService.findById(dto.restaurantId()) == null) 
            throw new IllegalArgumentException("Ресторан с таким ID не найден");
        if (visitorService.findById(dto.visitorId()) == null) 
            throw new IllegalArgumentException("Посетитель с таким ID не найден");
        VisitorRating entity = visitorRatingMapper.toEntity(dto);
        entity = new VisitorRating(visitorId, restaurantId, entity.getRating(), entity.getReviewText());
        visitorRatingRepository.update(entity);
        recalcRatingForRestaurant(restaurantId);
        return visitorRatingMapper.toDto(entity);
    }

    public boolean remove(Long visitorId, Long restaurantId) {
        boolean removed = visitorRatingRepository.remove(visitorId, restaurantId);
        recalcRatingForRestaurant(restaurantId);
        return removed;
    }

    public List<VisitorRatingResponse> findAll() {
        return visitorRatingRepository.findAll()
                .stream()
                .map(visitorRatingMapper::toDto)
                .toList();
    }

    public VisitorRatingResponse findById(Long visitorId, Long restaurantId) {
        VisitorRating entity = visitorRatingRepository.findById(visitorId, restaurantId);
        return visitorRatingMapper.toDto(entity);
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
