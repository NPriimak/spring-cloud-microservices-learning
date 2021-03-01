package com.learning.account.services;

import com.learning.account.mappers.AccountMapper;
import com.learning.account.model.dto.UpdateAccountDto;
import com.learning.account.model.entity.Account;
import com.learning.account.repos.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Optional<Account> getById(long accountId) {
        return accountRepository.getAccountByAccountId(accountId);
    }

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {
        Optional<Account> oldAccount = accountRepository.getAccountByAccountId(account.getAccountId());

        if(oldAccount.isPresent()){
            Account newAccount = oldAccount.get();
            newAccount.setEmail(account.getEmail());
            newAccount.setPhone(account.getPhone());
            newAccount.getBills().addAll(account.getBills());

            return accountRepository.save(newAccount);
        }


        return accountRepository.save(account);
    }


}
