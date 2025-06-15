package org.eliftunc.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.dto.AccountDto;
import org.eliftunc.transactionservice.entity.PaymentType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long transactionId;

    private AccountDto fromAccount;

    private AccountDto toAccount;

    private Double amount;

    private String description;

    private PaymentType paymentType;

    private Date transactionDate;
}
