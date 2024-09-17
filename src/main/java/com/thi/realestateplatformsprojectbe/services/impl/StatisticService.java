package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;
import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticTransactionDTO;
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
    public List<StatisticTransactionDTO> findTransactionByYear(Integer year) {
        List<Transaction> transactions = statisticRepository.findTransactionByYear(year);
        return transactions.stream().map(transaction -> StatisticTransactionDTO.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .nameEmployee(transaction.getEmployee().getName())
                .titleRealEstate(transaction.getRealEstate().getTitle())
                .nameBuyer(transaction.getBuyer().getName())
                .nameSeller(transaction.getSeller().getName())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<StatisticTransactionDTO> findTransactionByMonth(Integer year, Integer month) {
        List<Transaction> transactions = statisticRepository.findTransactionByMonth(month, year);
        return transactions.stream().map(transaction -> StatisticTransactionDTO.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .nameEmployee(transaction.getEmployee().getName())
                .titleRealEstate(transaction.getRealEstate().getTitle())
                .nameBuyer(transaction.getBuyer().getName())
                .nameSeller(transaction.getSeller().getName())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreateAt())
                .commissionFee(transaction.getCommissionFee())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .isDeleted(transaction.getIsDeleted())
                .build()).collect(Collectors.toList());
    }
}