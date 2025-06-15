package org.eliftunc.creditcardservice.kafka;

import lombok.RequiredArgsConstructor;
import org.eliftunc.creditcardservice.entity.CreditCard;
import org.eliftunc.creditcardservice.repository.CreditCardRepository;
import org.eliftunc.enums.PaymentType;
import org.eliftunc.events.PaymentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardEventListener {
    private final CreditCardRepository creditCardRepository;

    @KafkaListener(topics = "payment-service", groupId = "credi-card-service-group")
    public void consumerCreated(PaymentCreatedEvent paymentCreatedEvent){

        if(paymentCreatedEvent.getPaymentType() != PaymentType.CREDIT_CARD){
            throw new RuntimeException("Only credit card payments are supported.");
        }

        CreditCard creditCard = creditCardRepository.findCreditCardByUserId(paymentCreatedEvent.getUserId());

        creditCard.setAvailableLimit(creditCard.getAvailableLimit() - paymentCreatedEvent.getAmount());
        creditCard.setCurrentDebt(creditCard.getCurrentDebt() + paymentCreatedEvent.getAmount());

        creditCardRepository.save(creditCard);
    }
}
