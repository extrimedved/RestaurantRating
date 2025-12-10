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

        // Проверяем связи
        visitorService.findById(dto.visitorId());
        restaurantService.findById(dto.restaurantId());

        VisitorRating rating = visitorRatingMapper.toEntity(dto);
        rating = visitorRatingRepository.save(rating);

        recalcRating(dto.restaurantId());

        return visitorRatingMapper.toDto(rating);
    }

    public VisitorRatingResponse update(Long id, VisitorRatingRequest dto) {

        VisitorRating existing = visitorRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Оценка не найдена"));

        existing.setRating(dto.rating());
        existing.setReviewText(dto.reviewText());

        existing = visitorRatingRepository.save(existing);

        recalcRating(existing.getRestaurant().getId());

        return visitorRatingMapper.toDto(existing);
    }

    public boolean remove(Long id) {
        if (!visitorRatingRepository.existsById(id))
            return false;

        Long restaurantId = visitorRatingRepository.findById(id)
                .get()
                .getRestaurant()
                .getId();

        visitorRatingRepository.deleteById(id);

        recalcRating(restaurantId);

        return true;
    }

    public List<VisitorRatingResponse> findAll() {
        return visitorRatingRepository.findAll().stream()
                .map(visitorRatingMapper::toDto)
                .toList();
    }

    public VisitorRatingResponse findById(Long id) {
        return visitorRatingRepository.findById(id)
                .map(visitorRatingMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Оценка не найдена"));
    }

    private void recalcRating(Long restaurantId) {
        List<VisitorRating> ratings = visitorRatingRepository.findByRestaurantId(restaurantId);

        BigDecimal average = ratings.isEmpty()
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(ratings.stream()
                        .mapToInt(VisitorRating::getRating)
                        .average()
                        .orElse(0));

        restaurantService.updateAverageRating(restaurantId, average);
    }
}
