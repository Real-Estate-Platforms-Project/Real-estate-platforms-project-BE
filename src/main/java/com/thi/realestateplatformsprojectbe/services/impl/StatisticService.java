package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import com.thi.realestateplatformsprojectbe.repositories.IStatisticRepository;
import com.thi.realestateplatformsprojectbe.services.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<StatisticDemandDTO> findDemandByDay(LocalDate startDate, LocalDate endDate) {
        List<Demand> demands = statisticRepository.findDemandByDay(startDate, endDate);
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
    public List<Transaction> findTransactionByYear(Integer year) {
        List<Transaction> transactions = statisticRepository.findTransactionByYear(year);
        return transactions;
    }

    @Override
    public List<Transaction> findTransactionByMonth(Integer year, Integer month) {
        return statisticRepository.findTransactionByMonth(year, month);
    }
}
