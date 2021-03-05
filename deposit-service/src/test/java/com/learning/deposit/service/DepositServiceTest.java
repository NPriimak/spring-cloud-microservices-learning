package com.learning.deposit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.deposit.controllers.rest.AccountServiceClient;
import com.learning.deposit.controllers.rest.BillServiceClient;
import com.learning.deposit.mappers.DepositMapper;
import com.learning.deposit.model.dto.AccountResponseDto;
import com.learning.deposit.model.dto.BillResponseDto;
import com.learning.deposit.model.dto.DepositRequestDto;
import com.learning.deposit.model.dto.DepositResponseDto;
import com.learning.deposit.model.entity.Deposit;
import com.learning.deposit.repos.DepositRepository;
import com.learning.deposit.service.impl.DepositServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;
    @Mock
    private AccountServiceClient accountServiceClient;
    @Mock
    private BillServiceClient billServiceClient;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private DepositMapper depositMapper;
    @InjectMocks
    private DepositServiceImpl depositService;

    @Test
    public void depositServiceTest_deposit_withBillId(){
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong()))
                .thenReturn(createBillResponseDto());

        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong()))
                .thenReturn(createAccountResponseDto());

        Mockito.when(depositMapper.toEntity(ArgumentMatchers.any(DepositRequestDto.class)))
                .thenReturn(createDeposit());

        Mockito.when(depositMapper.toDto(ArgumentMatchers.any()))
                .thenReturn(createDepositResponseDto());

        DepositResponseDto deposit = depositService.deposit(
                DepositRequestDto.builder()
                        .billId(1L)
                        .amount(BigDecimal.valueOf(20_000))
                        .build());

        Assertions.assertEquals(deposit.getEmail(), "priymtestmail@gmail.com");
        Assertions.assertEquals(deposit.getAmount(), BigDecimal.valueOf(20_000));
        Assertions.assertEquals(deposit.getBillId(), 1L);

    }
    @Test
    public void depositServiceTest_deposit_withAccountId(){

        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong()))
                .thenReturn(createAccountResponseDto());

        Mockito.when(depositMapper.toEntity(ArgumentMatchers.any(DepositRequestDto.class)))
                .thenReturn(createDeposit());

        Mockito.when(depositMapper.toDto(ArgumentMatchers.any()))
                .thenReturn(createDepositResponseDto());

        Mockito.when(billServiceClient.getDefaultBill(ArgumentMatchers.anyLong())).thenReturn(createBillResponseDto());

        DepositResponseDto deposit = depositService.deposit(
                DepositRequestDto.builder()
                        .accountId(1L)
                        .amount(BigDecimal.valueOf(20_000))
                        .build());

        Assertions.assertEquals(deposit.getEmail(), "priymtestmail@gmail.com");
        Assertions.assertEquals(deposit.getAmount(), BigDecimal.valueOf(20_000));
        Assertions.assertEquals(deposit.getBillId(), 1L);

    }

    @Test(expected = IllegalArgumentException.class)
    public void depositServiceTest_deposit_withoutArgs(){
        depositService.deposit(
                DepositRequestDto.builder()
                .amount(BigDecimal.valueOf(20_000))
                .build()
        );
    }

    private DepositResponseDto createDepositResponseDto() {
        return DepositResponseDto.builder()
                .depositId(1L)
                .billId(1L)
                .amount(BigDecimal.valueOf(20_000))
                .creationTime(OffsetDateTime.now())
                .email("priymtestmail@gmail.com")
                .build();

    }

    private BillResponseDto createBillResponseDto() {
        return BillResponseDto.builder()
                .billId(1L)
                .accountId(1L)
                .amount(BigDecimal.valueOf(100000))
                .isDefault(true)
                .overdraftEnabled(true)
                .creationDate(OffsetDateTime.now())
                .build();
    }

    private AccountResponseDto createAccountResponseDto(){
        return AccountResponseDto.builder()
                .accountId(1L)
                .bills(Arrays.asList(1L, 2L, 3L))
                .email("priymtestmail@gmail.com")
                .creationDate(OffsetDateTime.now())
                .phone("123321")
                .name("test_1")
                .build();

    }

    private Deposit createDeposit(){
        return Deposit.builder()
                .billId(1L)
                .amount(BigDecimal.valueOf(20_000))
                .depositId(1L)
                .accountId(1L)
                .email("priymtestmail@gmail.com")
                .creationTime(OffsetDateTime.now())
                .build();
    }
}
