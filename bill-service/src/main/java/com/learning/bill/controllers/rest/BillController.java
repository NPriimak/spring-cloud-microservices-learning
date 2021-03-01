package com.learning.bill.controllers.rest;

import com.learning.bill.exceptions.BillNotFoundException;
import com.learning.bill.mappers.BillMapper;
import com.learning.bill.model.dto.BillRequestDto;
import com.learning.bill.model.dto.BillResponseDto;
import com.learning.bill.model.dto.UpdateBillDto;
import com.learning.bill.model.entity.Bill;
import com.learning.bill.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final BillMapper billMapper;

    @GetMapping("/{billId}")
    public BillResponseDto getBill(@PathVariable(name = "billId") long billId){
        return billService.getById(billId).map(billMapper::toDto)
                .orElseThrow(() -> new BillNotFoundException("Can't find bill with id: " + billId));
    }
    
    @GetMapping("/account/all/{accountId}")
    public List<BillResponseDto> getAllByAccountId(@PathVariable(name = "accountId") long accountId){
        Optional<List<Bill>> bills = billService.getBillsByAccountId(accountId);
        if (bills.isPresent()){
            return bills.get().stream()
                    .map(billMapper::toDto).collect(Collectors.toList());
        }

        throw new BillNotFoundException("Can't find bills fot account witn Id: " + accountId);
    }

    @GetMapping("/account/{accountId}")
    public BillResponseDto getDefaultBill(@PathVariable(name = "accountId") long accountId){
        return billMapper.toDto(billService.getDefaultBill(accountId));
    }

    @PostMapping("/add")
    public BillResponseDto addBill(@RequestBody BillRequestDto billRequestDto){
        return billMapper.toDto(
                billService.create(
                        billMapper.toEntity(billRequestDto)));
    }

    @PutMapping("/update")
    public BillResponseDto addBill(@RequestBody UpdateBillDto updateBillDto){
        return billMapper.toDto(
                billService.update(
                        billMapper.toEntity(updateBillDto)));
    }

}
