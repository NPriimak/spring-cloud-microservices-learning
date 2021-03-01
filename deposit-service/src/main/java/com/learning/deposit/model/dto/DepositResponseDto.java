package com.learning.deposit.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class DepositResponseDto {

    private final Long depositId;
    private final Long billId;
    private final BigDecimal amount;
    private final OffsetDateTime creationTime;
    private final String email;
}
