package org.eliftunc.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long accountId;
    private Long userId;
    private String accountNumber;
    private String iban;
    private Double balance;
    private ActiveStatus activeStatus;

}
