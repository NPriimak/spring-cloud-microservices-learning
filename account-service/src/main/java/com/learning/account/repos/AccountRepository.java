package com.learning.account.repos;

import com.learning.account.model.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> getAccountByAccountId(long accountId);
}
