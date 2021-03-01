package com.learning.deposit.service;

import com.learning.deposit.model.dto.DepositRequestDto;
import com.learning.deposit.model.dto.DepositResponseDto;
import com.learning.deposit.model.entity.Deposit;

import java.util.Optional;

public interface DepositService {

    Optional<Deposit> getById(long depositId);

    Deposit create(Deposit deposit);

    Deposit update(Deposit deposit);

    DepositResponseDto deposit(DepositRequestDto depositRequestDto);
}
