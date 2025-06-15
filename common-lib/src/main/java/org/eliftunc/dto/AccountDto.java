package org.eliftunc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long userId;
    private Long accountId;
    private String accountNumber;
    private Double balance;
    private ActiveStatus activeStatus;
}
