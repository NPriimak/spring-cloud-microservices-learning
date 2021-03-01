package com.learning.deposit.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositUpdateDto {
    private final Long billId;
    private final BigDecimal amount;
    private final String email;
}
