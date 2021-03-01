package com.learning.bill.services;

import com.learning.bill.model.entity.Bill;

import java.util.List;
import java.util.Optional;

public interface BillService {
    Optional<Bill> getById(long billId);
    Bill create(Bill bill);
    Bill update (Bill bill);
    Optional<List<Bill>> getBillsByAccountId(long accountId);
    Bill getDefaultBill(long accountId);
}
