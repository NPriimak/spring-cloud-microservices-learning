package com.learning.account.model.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class AccountRequestDto {

    private String name;
    private String email;
    private String phone;
    private List<Long> bills;
}
