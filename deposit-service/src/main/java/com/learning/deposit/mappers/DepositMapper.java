package com.learning.deposit.mappers;

import com.learning.deposit.model.dto.DepositRequestDto;
import com.learning.deposit.model.dto.DepositResponseDto;
import com.learning.deposit.model.dto.DepositUpdateDto;
import com.learning.deposit.model.entity.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DepositMapper {
    public abstract Deposit toEntity(DepositRequestDto depositDto);
    public abstract Deposit toEntity(DepositResponseDto depositDto);
    public abstract Deposit toEntity(DepositUpdateDto depositDto);

    public abstract DepositResponseDto toDto(Deposit deposit);


}
