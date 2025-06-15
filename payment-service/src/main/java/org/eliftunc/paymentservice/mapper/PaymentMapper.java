package org.eliftunc.paymentservice.mapper;

import org.eliftunc.events.PaymentCreatedEvent;
import org.eliftunc.paymentservice.dto.PaymentRequestDto;
import org.eliftunc.paymentservice.dto.PaymentResponseDto;
import org.eliftunc.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "paymentStatus", constant = "SUCCESS")
    Payment toPayment(PaymentRequestDto dto);

    @Mapping(target = "source", ignore = true)
    PaymentResponseDto toPaymentResponseDto(Payment payment);

    PaymentCreatedEvent toPaymentCreatedEvent(Payment payment);
}