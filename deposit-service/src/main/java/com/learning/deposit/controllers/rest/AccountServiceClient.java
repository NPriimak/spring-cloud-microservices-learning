package com.learning.deposit.controllers.rest;

import com.learning.deposit.model.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/{accountId}")
    AccountResponseDto getAccountById(@PathVariable(name = "accountId") long accountId);
}
