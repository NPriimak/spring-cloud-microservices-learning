package com.learning.account.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountId;
    private String email;
    private String name;
    private String phone;
    @CreatedDate
    @CreationTimestamp
    private OffsetDateTime creationDate;
    @ElementCollection
    private List<Long> bills;

    public Account(Long accountId, String email, String name, String phone, List<Long> bills) {
        this.accountId = accountId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.bills = bills;
    }
}
