package com.learning.bill.repos;

import com.learning.bill.model.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends CrudRepository<Bill, Long> {
    Optional<List<Bill>> findBillsByAccountId(long accountId);
}
