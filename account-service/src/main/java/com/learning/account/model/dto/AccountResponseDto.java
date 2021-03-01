package com.learning.account.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class AccountResponseDto {

    private final Long accountId;
    private final String email;
    private final String name;
    private final String phone;
    private final OffsetDateTime creationDate;
    private final List<Long> bills;
}
