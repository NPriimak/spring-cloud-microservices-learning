package com.learning.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learning.notification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        log.info("RECEIVE: " + message);
        String jsonBody = new String(message.getBody());
        objectMapper.registerModule(new JavaTimeModule());
        DepositResponseDto depositResponseDto = objectMapper
                .readValue(jsonBody, DepositResponseDto.class);
        log.info("MAP A NEW DTO: " + depositResponseDto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDto.getEmail());
        mailMessage.setFrom("notification-service");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit with sum: " + depositResponseDto.getAmount());

        try{
            javaMailSender.send(mailMessage);
        } catch (Exception e){
            log.warn("EXCEPTION IN MAIL SERVICE " + e.getClass());
            e.printStackTrace();
        }
    }

}
