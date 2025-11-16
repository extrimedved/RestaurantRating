package com.example.restaurantrating.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurantrating.domain.entity.Visitor;
import com.example.restaurantrating.repository.VisitorRepository;

@Service
public class VisitorService {
     private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }
    public Visitor save(Visitor visitor) {
        if(visitor == null)
            throw new IllegalArgumentException("Посетитель равен null");
        if (visitor.getId() == null) 
            throw new IllegalArgumentException("ID обязателен");
        if (visitor.getAge() == null) 
            throw new IllegalArgumentException("Возраст обязателен");
        if (visitor.getGender() == null) 
            throw new IllegalArgumentException("Пол обязателен");
        return visitorRepository.save(visitor);
    }

    public boolean remove(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Введите id пользователя для удаления");
        }
        return visitorRepository.remove(id);
    }

    public Visitor findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Введите id пользователя для поиска по id");
        }
        Visitor visitor = visitorRepository.findById(id);
        if (visitor == null) {
            throw new RuntimeException("Посетитель не найден");
        }
        return visitor;
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}
