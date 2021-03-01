package com.learning.bill.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class BillRequestDto {
    private final Long accountId;
    private final BigDecimal amount;
    private final Boolean isDefault;
    private final Boolean overdraftEnabled;
}
