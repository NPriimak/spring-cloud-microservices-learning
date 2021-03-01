package com.learning.account.mappers;

import com.learning.account.model.dto.AccountRequestDto;
import com.learning.account.model.dto.AccountResponseDto;
import com.learning.account.model.dto.UpdateAccountDto;
import com.learning.account.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    public abstract Account toEntity(AccountResponseDto accountDto);
    public abstract Account toEntity(AccountRequestDto accountDto);
    public abstract Account toEntity(UpdateAccountDto accountDto);

    public abstract AccountResponseDto toDto(Account account);






}
