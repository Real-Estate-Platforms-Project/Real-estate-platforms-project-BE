package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;
import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticTransactionDTO;
import com.thi.realestateplatformsprojectbe.models.Transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IStatisticService {
    List<StatisticDemandDTO> findDemandByYear(Integer year);

    List<StatisticDemandDTO> findDemandByMonth(Integer year, Integer month);

    List<StatisticDemandDTO> findDemandByDay(LocalDate startDate, LocalDate endDate);

    List<StatisticTransactionDTO> findTransactionByYear(Integer year);

    List<StatisticTransactionDTO> findTransactionByMonth(Integer year, Integer month);


}
