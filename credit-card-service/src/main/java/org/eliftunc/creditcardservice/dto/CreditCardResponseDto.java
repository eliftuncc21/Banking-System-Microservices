package org.eliftunc.creditcardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponseDto {
    private Long cardId;
    private Long userId;
    private String cardNumber;
    private Integer cvv;
    private Double cardLimit;
    private Double availableLimit;
    private Double currentDebt;
    private Date statementDate;
    private Date dueDate;
    private ActiveStatus activeStatus;
    private Date createdDate;
}
