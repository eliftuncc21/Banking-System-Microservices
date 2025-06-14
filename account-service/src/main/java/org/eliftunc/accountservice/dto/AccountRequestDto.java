package org.eliftunc.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    @NotNull
    private Long userId;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String iban;

    @NotNull
    private Double balance;

    @NotNull
    private ActiveStatus activeStatus;

}
