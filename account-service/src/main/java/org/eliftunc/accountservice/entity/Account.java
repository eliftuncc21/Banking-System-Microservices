package org.eliftunc.accountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "iban", nullable = false, length = 20)
    private String iban;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status", nullable = false)
    private ActiveStatus activeStatus;

}
