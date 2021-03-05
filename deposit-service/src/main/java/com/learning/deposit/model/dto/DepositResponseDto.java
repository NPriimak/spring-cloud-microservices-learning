package com.learning.deposit.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DepositResponseDto {

    private final Long depositId;
    private final Long billId;
    private final BigDecimal amount;
    private final OffsetDateTime creationTime;
    private final String email;
}
