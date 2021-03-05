package com.learning.deposit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learning.deposit.controllers.rest.AccountServiceClient;
import com.learning.deposit.controllers.rest.BillServiceClient;
import com.learning.deposit.exceptions.DepositServiceException;
import com.learning.deposit.mappers.DepositMapper;
import com.learning.deposit.model.dto.*;
import com.learning.deposit.model.entity.Deposit;
import com.learning.deposit.repos.DepositRepository;
import com.learning.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final DepositMapper depositMapper;

    @Override
    public Optional<Deposit> getById(long depositId) {
        return depositRepository.findById(depositId);
    }

    @Override //in progress
    public Deposit update(Deposit deposit) {
        Optional<Deposit> oldDeposit = depositRepository.findById(deposit.getDepositId());
        if (oldDeposit.isPresent()){
            Deposit newDeposit = oldDeposit.get();
            newDeposit.setAmount(deposit.getAmount());
            newDeposit.setEmail(deposit.getEmail());

            return depositRepository.save(newDeposit);
        }

        return depositRepository.save(deposit);
    }

    @Override
    public DepositResponseDto deposit(DepositRequestDto depositRequestDto){
        AccountResponseDto accountResponseDto;
        BillResponseDto targetBill;
        DepositResponseDto depositResponseDto;

        if (depositRequestDto.getAccountId() == null && depositRequestDto.getBillId() == null) {
            throw new IllegalArgumentException("Invalid account or bill Id");
        }

        if (depositRequestDto.getBillId() == null) {
            accountResponseDto = accountServiceClient.getAccountById(depositRequestDto.getAccountId());
            targetBill = billServiceClient.getDefaultBill(depositRequestDto.getAccountId());
        } else {
            targetBill = billServiceClient.getBillById(depositRequestDto.getBillId());
            accountResponseDto = accountServiceClient.getAccountById(targetBill.getAccountId());
        }
        updateBill(targetBill, depositRequestDto);

        Deposit deposit = depositMapper.toEntity(depositRequestDto);
        deposit.setEmail(accountResponseDto.getEmail());
        depositResponseDto = depositMapper.toDto(depositRepository.save(deposit));

        sendNotification(depositResponseDto);

        return depositResponseDto;
    }

    private void sendNotification(DepositResponseDto depositResponseDto) {
        objectMapper.registerModule(new JavaTimeModule());
        try {
            rabbitTemplate.convertAndSend(
                    TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, objectMapper.writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Error while mapping");
        }
    }

    private void updateBill(BillResponseDto billResponseDto, DepositRequestDto depositRequestDto){
        billServiceClient.update(
                UpdateBillDto.builder()
                        .billId(billResponseDto.getBillId())
                        .amount(billResponseDto.getAmount().add(depositRequestDto.getAmount()))
                        .overdraftEnabled(billResponseDto.getOverdraftEnabled())
                        .isDefault(billResponseDto.getIsDefault())
                        .build());
    }
}
