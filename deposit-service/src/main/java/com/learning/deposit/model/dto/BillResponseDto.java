package com.learning.deposit.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillResponseDto {

    private final Long billId;
    private final Long accountId;
    private final BigDecimal amount;
    private final Boolean isDefault;
    private final Boolean overdraftEnabled;
    private final OffsetDateTime creationDate;
}
