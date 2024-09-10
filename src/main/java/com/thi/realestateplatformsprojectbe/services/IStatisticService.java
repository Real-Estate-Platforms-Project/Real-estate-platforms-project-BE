package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;

import java.util.Date;
import java.util.List;

public interface IStatisticService {
    List<StatisticDemandDTO> findDemandByYear(Integer year);

    List<StatisticDemandDTO> findDemandByMonth(Integer year, Integer month);

    List<StatisticDemandDTO> findDemandByDay(Date day);


}
