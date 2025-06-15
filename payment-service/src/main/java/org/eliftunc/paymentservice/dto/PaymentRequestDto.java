package org.eliftunc.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.PaymentType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Double amount;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    private Date processedAt;
    @NotBlank
    private String description;
}
