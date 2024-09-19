package com.thi.realestateplatformsprojectbe.dto.statisticDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class StatisticTransactionDTO {
    Long id;
    String code;
    String nameEmployee;
    String titleRealEstate;
    String nameBuyer;
    String nameSeller;
    Double amount;
    LocalDate createdAt;
    BigDecimal commissionFee;
    String description;
    String status;
    Boolean isDeleted;
}
