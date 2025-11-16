package com.example.restaurantrating.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.restaurantrating.domain.entity.VisitorRating;

@Repository
public class VisitorRatingRepository {
    private final List<VisitorRating> visitorRatings = new ArrayList<>();

     public VisitorRating save(VisitorRating visitorRating) {
        for (int i = 0; i < visitorRatings.size(); i++) {
            VisitorRating vr = visitorRatings.get(i);
            if (vr.getVisitorId().equals(visitorRating.getVisitorId()) &&
                vr.getRestaurantId().equals(visitorRating.getRestaurantId())) {
                visitorRatings.remove(i);
                break;
            }
        }

        visitorRatings.add(visitorRating);
        return visitorRating;
    }

    public boolean remove(Long visitorId, Long restaurantId) {
        return visitorRatings.removeIf(r -> r.getVisitorId().equals(visitorId)
                                  && r.getRestaurantId().equals(restaurantId));
    }

    public List<VisitorRating> findAll() {
        return Collections.unmodifiableList(visitorRatings);
    }

    public VisitorRating findById(Long visitorId, Long restaurantId) {
        return visitorRatings.stream()
                .filter(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId))
                .findFirst().orElse(null);
    }

    public List<VisitorRating> findByRestaurantId(Long restaurantId) {
        List<VisitorRating> list = new ArrayList<>();
        for (VisitorRating vr : visitorRatings) {
            if (vr.getRestaurantId().equals(restaurantId)) list.add(vr);
        }
        return list;
    }
}
