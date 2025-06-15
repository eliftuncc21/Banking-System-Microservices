package org.eliftunc.creditcardservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "credit_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card-number", nullable = false, unique = true, length = 20)
    private String cardNumber;

    @Column(name = "cvv", nullable = false, length = 3)
    private Integer cvv;

    @Column(name = "card_limit")
    private Double cardLimit;

    @Column(name = "available_limit", nullable = false)
    private Double availableLimit;

    @Column(name = "current_debt", nullable = false)
    private Double currentDebt;

    @Column(name = "statement_date")
    private Date statementDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private ActiveStatus activeStatus;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;
}