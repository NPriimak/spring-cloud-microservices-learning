package com.learning.deposit.repos;

import com.learning.deposit.model.entity.Deposit;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepository extends CrudRepository<Deposit, Long> {

}
