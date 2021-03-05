package com.learning.deposit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.deposit.DepositApplication;
import com.learning.deposit.config.SpringH2DataBaseConfig;
import com.learning.deposit.controllers.rest.AccountServiceClient;
import com.learning.deposit.controllers.rest.BillServiceClient;
import com.learning.deposit.mappers.DepositMapper;
import com.learning.deposit.model.dto.AccountResponseDto;
import com.learning.deposit.model.dto.BillResponseDto;
import com.learning.deposit.model.dto.DepositRequestDto;
import com.learning.deposit.model.dto.DepositResponseDto;
import com.learning.deposit.model.entity.Deposit;
import com.learning.deposit.repos.DepositRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DepositApplication.class,
        SpringH2DataBaseConfig.class
})

public class DepositControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BillServiceClient billServiceClient;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private DepositMapper depositMapper;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private static final String REQUEST = "{\n" +
            "   \"billId\": 1,\n" +
            "   \"amount\": 20000\n" +
            "}";

    @Test
    public void deposit() throws Exception {
        BillResponseDto billResponseDto = createBillResponseDto();
        AccountResponseDto accountResponseDto = createAccountResponseDto();

        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong()))
                .thenReturn(billResponseDto);

        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong()))
                .thenReturn(accountResponseDto);

        Mockito.when(depositMapper.toEntity(ArgumentMatchers.any(DepositRequestDto.class)))
                .thenReturn(createDeposit());

        Mockito.when(depositMapper.toDto(ArgumentMatchers.any()))
                .thenReturn(createDepositResponseDto());

        MvcResult mvcResult = mockMvc.perform(post("/add")
                .content(REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        DepositResponseDto depositResponseDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), DepositResponseDto.class);
        Assertions.assertEquals(depositResponseDto.getBillId(), 1L);
        Assertions.assertEquals(depositResponseDto.getEmail(), "priymtestmail@gmail.com");
        Assertions.assertEquals(depositResponseDto.getAmount(), BigDecimal.valueOf(20_000));
        Assertions.assertEquals(depositResponseDto.getDepositId(), 1L);


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


