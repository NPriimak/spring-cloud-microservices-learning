package com.learning.deposit.controllers.rest;

import com.learning.deposit.model.dto.BillResponseDto;
import com.learning.deposit.model.dto.UpdateBillDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("bill-service")
public interface BillServiceClient {

    @GetMapping("bills/{billId}")
    BillResponseDto getBillById(@PathVariable(name = "billId") long billId);

    @PutMapping("bills/update")
    void update(@RequestBody UpdateBillDto updateBillDto);

    @GetMapping("bills/account/all/{accountId}")
    List<BillResponseDto> getBillByAccountId(@PathVariable(name = "accountId") long accountId);

    @GetMapping("bills/account/{accountId}")
    BillResponseDto getDefaultBill(@PathVariable(name = "accountId") long accountId);

}
