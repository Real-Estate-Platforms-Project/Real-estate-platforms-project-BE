package com.thi.realestateplatformsprojectbe.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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

    Long employeeId;

    Long realEstateId;

    Long buyerId;

    Long SellerId;

    Integer amount;

    LocalDate createAt;

    Integer commissionFee;

    String description;

    String status;

    Boolean isDeleted;
}
