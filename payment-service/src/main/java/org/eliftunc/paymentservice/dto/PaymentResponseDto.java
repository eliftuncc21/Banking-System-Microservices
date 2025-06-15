package org.eliftunc.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eliftunc.enums.PaymentType;
import org.eliftunc.paymentservice.entity.PaymentStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto<T> {
    private Long paymentId;
    private T source;
    private PaymentStatus paymentStatus;
    private Double amount;
    private PaymentType paymentType;
    private Date processedAt;
    private String description;
}
