package com.thi.realestateplatformsprojectbe.dto.response;

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
public class TransactionResponse {

    Long id;

    String code;

    String employee;

    String realEstate;

    String buyer;

    String seller;

    Double amount;

    LocalDate createAt;

    Integer commissionFee;

    String description;

    String status;

    Boolean isDeleted;
}
