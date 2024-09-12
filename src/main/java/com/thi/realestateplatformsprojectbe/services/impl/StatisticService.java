package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.repositories.IStatisticRepository;
import com.thi.realestateplatformsprojectbe.services.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final IStatisticRepository statisticRepository;
    @Override
    public List<StatisticDemandDTO> findDemandByYear(Integer year) {
        List<Demand> demands = statisticRepository.findDemandByYear(year);
        return demands.stream().map(demand -> StatisticDemandDTO.builder()
                .id(demand.getId())
                .title(demand.getTitle())
                .nameBuyer(demand.getBuyer().getName())
                .type(demand.getType())
                .realEstateType(demand.getRealEstateType())
                .region(demand.getRegion())
                .minArea(demand.getMinArea())
                .maxArea(demand.getMaxArea())
                .createdAt(demand.getCreatedAt())
                .notes(demand.getNotes())
                .isVerify(demand.getIsVerify())
                .isDeleted(demand.getIsDeleted())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<StatisticDemandDTO> findDemandByMonth(Integer year, Integer month) {
        List<Demand> demands = statisticRepository.findDemandByMonth(month, year);
        return demands.stream().map(demand -> StatisticDemandDTO.builder()
                .id(demand.getId())
                .title(demand.getTitle())
                .nameBuyer(demand.getBuyer().getName())
                .type(demand.getType())
                .realEstateType(demand.getRealEstateType())
                .region(demand.getRegion())
                .minArea(demand.getMinArea())
                .maxArea(demand.getMaxArea())
                .createdAt(demand.getCreatedAt())
                .notes(demand.getNotes())
                .isVerify(demand.getIsVerify())
                .isDeleted(demand.getIsDeleted())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<StatisticDemandDTO> findDemandByDay(Date day) {
        return null;
    }
}
