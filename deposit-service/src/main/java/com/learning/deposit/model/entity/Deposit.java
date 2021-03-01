package com.learning.deposit.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "deposits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long depositId;
    private Long billId;
    private Long accountId;
    private BigDecimal amount;

    @CreatedDate
    @CreationTimestamp
    private OffsetDateTime creationTime;

    private String email;

}
