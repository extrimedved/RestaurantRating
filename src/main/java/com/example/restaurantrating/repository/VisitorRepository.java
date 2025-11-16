package com.example.restaurantrating.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.restaurantrating.domain.entity.Visitor;

@Repository
public class VisitorRepository {

    private final List<Visitor> visitors = new ArrayList<>();

    public Visitor save(Visitor visitor) {
        for (int i = 0; i < visitors.size(); i++) {
            if (visitors.get(i).getId().equals(visitor.getId())) {
                visitors.remove(i);
                break;
            }
        }
        visitors.add(visitor);
        return visitor;
    }

    public boolean remove(Long id) {
        return visitors.removeIf(v -> v.getId().equals(id));
    }

    public List<Visitor> findAll() {
        return Collections.unmodifiableList(visitors);
    }

    public Visitor findById(Long id) {
        return visitors.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }
}
