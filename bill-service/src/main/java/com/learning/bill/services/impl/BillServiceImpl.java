package com.learning.bill.services.impl;

import com.learning.bill.controllers.rest.AccountServiceClient;
import com.learning.bill.exceptions.BillNotFoundException;
import com.learning.bill.model.dto.AccountResponseDto;
import com.learning.bill.model.dto.UpdateAccountDto;
import com.learning.bill.model.entity.Bill;
import com.learning.bill.repos.BillRepository;
import com.learning.bill.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final AccountServiceClient accountServiceClient;

    @Override
    public Optional<Bill> getById(long billId) {
        return billRepository.findById(billId);
    }

    @Override
    public Bill create(Bill bill) {
        Bill newBill = billRepository.save(bill);
        AccountResponseDto accountById = accountServiceClient.getAccountById(bill.getAccountId());
        accountServiceClient.updateAccount(UpdateAccountDto.builder()
                .accountId(accountById.getAccountId())
                .bills(Collections.singletonList(newBill.getBillId()))
                .email(accountById.getEmail())
                .phone(accountById.getPhone())
                .build());

        return newBill;
    }

    @Override
    public Bill update(Bill bill) {
        Optional<Bill> oldBill = billRepository.findById(bill.getBillId());
        if (oldBill.isPresent()){
            Bill newBill = oldBill.get();
            newBill.setAmount(bill.getAmount());
            newBill.setIsDefault(bill.getIsDefault());
            newBill.setOverdraftEnabled(bill.getOverdraftEnabled());

            return billRepository.save(newBill);
        }
        return billRepository.save(bill);
    }

    @Override
    public Optional<List<Bill>> getBillsByAccountId(long accountId) {
        return billRepository.findBillsByAccountId(accountId);
    }

    @Override
    public Bill getDefaultBill(long accountId) {
        Optional<List<Bill>> billsByAccountId = getBillsByAccountId(accountId);
        return billsByAccountId.get().stream().filter(Bill::getIsDefault).findFirst().orElseThrow(() ->new BillNotFoundException("Can't find default bill"));


    }
}
