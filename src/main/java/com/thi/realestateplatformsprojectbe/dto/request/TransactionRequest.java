package com.thi.realestateplatformsprojectbe.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    Long id;

    String code;

    Long employee;

    Long realEstate;

    Long buyer;

    Long seller;

    Integer amount;

    LocalDate createAt;

    BigDecimal commissionFee;

    String description;

    String status;

    Boolean isDeleted;
}
