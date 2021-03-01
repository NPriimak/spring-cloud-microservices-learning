package com.learning.account.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateAccountDto {
    private final Long accountId;
    private final String email;
    private final String phone;
    private final List<Long> bills ;
}
