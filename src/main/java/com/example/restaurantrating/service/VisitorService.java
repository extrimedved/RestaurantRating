package com.example.restaurantrating.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurantrating.domain.entity.Visitor;
import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.mapper.VisitorMapper;
import com.example.restaurantrating.repository.VisitorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;

    public VisitorResponse create(VisitorRequest dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor = visitorRepository.save(visitor);
        return visitorMapper.toDto(visitor);
    }

    public VisitorResponse update(Long id, VisitorRequest dto) {
        Visitor existing = visitorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Посетитель не найден"));

        existing.setName(dto.name());
        existing.setAge(dto.age());
        existing.setGender(dto.gender());

        existing = visitorRepository.save(existing);
        return visitorMapper.toDto(existing);
    }

    public boolean remove(Long id) {
        if (!visitorRepository.existsById(id))
            return false;

        visitorRepository.deleteById(id);
        return true;
    }

    public VisitorResponse findById(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Посетитель не найден"));

        return visitorMapper.toDto(visitor);
    }

    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toDto)
                .toList();
    }
}
