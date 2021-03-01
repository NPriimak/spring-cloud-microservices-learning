package com.learning.notification.services;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class DepositResponseDto {

    private Long depositId;
    private Long billId;
    private BigDecimal amount;
    private OffsetDateTime creationTime;
    private String email;

}
