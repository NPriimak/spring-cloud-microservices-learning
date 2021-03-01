package com.learning.deposit.model.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DepositRequestDto {

    private Long accountId;
    private BigDecimal amount;
    private Long billId;
}
