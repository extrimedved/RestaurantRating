package com.example.restaurantrating.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.restaurantrating.domain.entity.Visitor;

@Repository
public class VisitorRepository {

    private final List<Visitor> visitors = new ArrayList<>();
    private long nextId = 1; 

     public Visitor create(Visitor visitor) {
        visitor = new Visitor(nextId++, visitor.getName(),
                visitor.getAge(), visitor.getGender());
        visitors.add(visitor);
        return visitor;
    }

    public Visitor update(Visitor visitor) {
        for (int i = 0; i < visitors.size(); i++) {
            if (visitors.get(i).getId().equals(visitor.getId())) {
                visitors.set(i, visitor); 
                return visitor;
            }
        }
        throw new RuntimeException("Посетитель не найден");
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
