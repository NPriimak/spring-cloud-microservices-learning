package com.learning.deposit.controllers.rest;

import com.learning.deposit.exceptions.DepositNotFoundException;
import com.learning.deposit.mappers.DepositMapper;
import com.learning.deposit.model.dto.DepositRequestDto;
import com.learning.deposit.model.dto.DepositResponseDto;
import com.learning.deposit.model.dto.DepositUpdateDto;
import com.learning.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class DepositController {

    private final DepositMapper depositMapper;
    private final DepositService depositService;

    @GetMapping("/{depositId}")
    public DepositResponseDto getById(@PathVariable(name = "depositId") long depositId) {
        return depositService.getById(depositId)
                .map(depositMapper::toDto)
                .orElseThrow(() -> new DepositNotFoundException("Can't find deposut with id: " + depositId));
    }

    @PostMapping("/add")
    public DepositResponseDto deposit(@RequestBody DepositRequestDto depositRequestDto) {
        return depositService.deposit(depositRequestDto);
    }

    @PutMapping("/update")
    public DepositResponseDto update(@RequestBody DepositUpdateDto depositUpdateDto) {
        return depositMapper.toDto(
                depositService.update(
                        depositMapper.toEntity(depositUpdateDto)));
    }
}
