package org.eliftunc.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.client.AccountServiceClient;
import org.eliftunc.client.UserServiceClient;
import org.eliftunc.dto.AccountDto;
import org.eliftunc.enums.PaymentType;
import org.eliftunc.events.PaymentCreatedEvent;
import org.eliftunc.paymentservice.client.CreditCard;
import org.eliftunc.paymentservice.client.CreditCardServiceClient;
import org.eliftunc.paymentservice.dto.PaymentRequestDto;
import org.eliftunc.paymentservice.dto.PaymentResponseDto;
import org.eliftunc.paymentservice.entity.Payment;
import org.eliftunc.paymentservice.mapper.PaymentMapper;
import org.eliftunc.paymentservice.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;
    private final AccountServiceClient accountServiceClient;
    private final CreditCardServiceClient creditCardServiceClient;

    public void payment(PaymentRequestDto paymentRequestDto) {
        validatePayment(paymentRequestDto);

        Payment payment = paymentMapper.toPayment(paymentRequestDto);
        paymentRepository.save(payment);

        PaymentCreatedEvent event = paymentMapper.toPaymentCreatedEvent(payment);
        kafkaTemplate.send("payment-service", event);
    }

    public Page<PaymentResponseDto<?>> paymentAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findAll(pageable).map(this::getPaymentResponseDto);
    }

    public PaymentResponseDto<?> getPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId).map(this::getPaymentResponseDto)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    private PaymentResponseDto<?> getPaymentResponseDto(Payment payment) {
        if(payment.getPaymentType().equals(PaymentType.BANK_CARD)){
            PaymentResponseDto<AccountDto> paymentResponseDto = paymentMapper.toPaymentResponseDto(payment);
            paymentResponseDto.setSource(accountServiceClient.getAccountByUserId(payment.getUserId()));
            return paymentResponseDto;
        } else if (payment.getPaymentType().equals(PaymentType.CREDIT_CARD)){
            PaymentResponseDto<CreditCard> paymentResponseDto = paymentMapper.toPaymentResponseDto(payment);
            paymentResponseDto.setSource(creditCardServiceClient.getCreditCardByUserId(payment.getUserId()));
            return paymentResponseDto;
        } else {
            return null;
        }
    }

    private void validatePayment(PaymentRequestDto paymentRequestDto) {
        switch (paymentRequestDto.getPaymentType()){
            case BANK_CARD -> {
                AccountDto account = accountServiceClient.getAccountById(paymentRequestDto.getUserId());
                if(account.getBalance() < paymentRequestDto.getAmount()){
                    throw new RuntimeException("Insufficient balance for the transaction.");
                }
            }
            case CREDIT_CARD -> {
                CreditCard creditCard = creditCardServiceClient.getCreditCardByUserId(paymentRequestDto.getUserId());
                if (creditCard.getAvailableLimit() < paymentRequestDto.getAmount()){
                    throw new RuntimeException("Insufficient balance for the transaction.");
                }
            }

            default -> throw new RuntimeException("Unsupported payment type.");
        }
    }
}