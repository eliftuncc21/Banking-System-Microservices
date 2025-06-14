package org.eliftunc.creditcardservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreditCardRequestDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String cardNumber;

    @NotNull
    private Integer cvv;

    @NotNull
    private Double cardLimit;

    @NotNull
    private Double availableLimit;

    @NotNull
    private Double currentDebt;

    @NotNull
    private Date statementDate;

    @NotNull
    private Date dueDate;

    @NotNull
    private ActiveStatus activeStatus;
}