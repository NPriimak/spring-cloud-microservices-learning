package com.learning.bill.controllers.rest;

import com.learning.bill.model.dto.AccountResponseDto;
import com.learning.bill.model.dto.UpdateAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/{accountId}")
    AccountResponseDto getAccountById(@PathVariable(name = "accountId") long accountId);

    @PutMapping("/accounts/update")
    AccountResponseDto updateAccount(@RequestBody UpdateAccountDto updateAccountDto);
}