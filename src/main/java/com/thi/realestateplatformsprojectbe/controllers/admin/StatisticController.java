package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.dto.statisticDTO.StatisticDemandDTO;
import com.thi.realestateplatformsprojectbe.services.IStatisticService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
@CrossOrigin("*")
public class StatisticController {

    private final IStatisticService statisticService;

    @GetMapping("/demands/year")
    @PermitAll
    public ResponseEntity<?> getStatisticDemandByYear(@RequestParam(required = false) Integer year) {
        List<StatisticDemandDTO> list = statisticService.findDemandByYear(year);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/demands/month")
    @PermitAll
    public ResponseEntity<?> getStatisticDemandByMonth(@RequestParam(required = false) Integer year,
                                                       @RequestParam(required = false) Integer month) {
        List<StatisticDemandDTO> list = statisticService.findDemandByMonth(year, month);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
