package com.learning.account.controllers.rest;

import com.learning.account.exceptons.AccountNotFoundException;
import com.learning.account.mappers.AccountMapper;
import com.learning.account.model.dto.AccountRequestDto;
import com.learning.account.model.dto.AccountResponseDto;
import com.learning.account.model.dto.UpdateAccountDto;
import com.learning.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable(name = "accountId") long accountId) {
        return accountService.getById(accountId).map(accountMapper::toDto)
                .orElseThrow(()-> new AccountNotFoundException("Can't find account with id: " + accountId));

    }

    @PostMapping("/add")
    public AccountResponseDto addAccount(@RequestBody AccountRequestDto accountRequestDto){
       return accountMapper.toDto(
               accountService.create(
                       accountMapper.toEntity(accountRequestDto)));
    }

    @PutMapping("/update")
    public AccountResponseDto updateAccount(@RequestBody UpdateAccountDto updateAccountDto){
        return accountMapper.toDto(
                accountService.update(
                        accountMapper.toEntity(updateAccountDto)));
    }
}
