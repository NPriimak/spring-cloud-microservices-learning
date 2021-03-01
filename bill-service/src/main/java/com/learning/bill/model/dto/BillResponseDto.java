package com.learning.bill.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class BillResponseDto {

    private final Long billId;
    private final Long accountId;
    private final BigDecimal amount;
    private final Boolean isDefault;
    private final Boolean overdraftEnabled;
    private final OffsetDateTime creationDate;
}
