package org.eliftunc.paymentservice.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    private Long cardId;
    private String cardNumber;
    private Double availableLimit;
    private Double currentDebt;
    private ActiveStatus activeStatus;
}
