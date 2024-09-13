package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Position;
import com.thi.realestateplatformsprojectbe.repositories.IPositionRepository;
import com.thi.realestateplatformsprojectbe.services.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService implements IPositionService {

    @Autowired
    private IPositionRepository positionRepository;
    @Override
    public Iterable<Position> getAllPosition() {
        return positionRepository.findAll();
    }
}
