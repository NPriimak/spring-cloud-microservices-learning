package com.learning.account.services;

import com.learning.account.model.dto.UpdateAccountDto;
import com.learning.account.model.entity.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getById(long accountId);
    Account create(Account account);
    Account update(Account account);


}
