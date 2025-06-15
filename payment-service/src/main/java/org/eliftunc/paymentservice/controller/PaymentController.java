package org.eliftunc.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.eliftunc.paymentservice.dto.PaymentRequestDto;
import org.eliftunc.paymentservice.dto.PaymentResponseDto;
import org.eliftunc.paymentservice.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public void payment(@RequestBody PaymentRequestDto paymentRequestDto) {
        paymentService.payment(paymentRequestDto);
    }

    @GetMapping("/payment-list")
    public Page<PaymentResponseDto<?>> listPayments(@RequestParam int page, @RequestParam int size) {
        return paymentService.paymentAll(page, size);
    }

    @GetMapping("/payment/{id}")
    public PaymentResponseDto<?> getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }
}
