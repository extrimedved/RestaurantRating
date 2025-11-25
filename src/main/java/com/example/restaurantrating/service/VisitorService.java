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
        visitorRepository.create(visitor);
        return visitorMapper.toDto(visitor);
    }

    public VisitorResponse update(Long id, VisitorRequest dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor = new Visitor(id, visitor.getName(), visitor.getAge(), visitor.getGender()); 
        visitorRepository.update(visitor);
        return visitorMapper.toDto(visitor);
    }

    public boolean remove(Long id) {
        return visitorRepository.remove(id);
    }

    public VisitorResponse findById(Long id) {
        Visitor visitor = visitorRepository.findById(id);
        return visitorMapper.toDto(visitor);
    }

    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll()
                .stream()
                .map(visitorMapper::toDto)
                .toList();
    }
}
