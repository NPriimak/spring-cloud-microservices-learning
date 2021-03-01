package com.learning.bill.model.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateBillDto {

    private Long billId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
}
