package org.eliftunc.accountservice.kafka;

import lombok.RequiredArgsConstructor;
import org.eliftunc.accountservice.entity.Account;
import org.eliftunc.accountservice.repository.AccountRepository;
import org.eliftunc.enums.PaymentType;
import org.eliftunc.events.PaymentCreatedEvent;
import org.eliftunc.events.TransactionCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventListener {
    private final AccountRepository accountRepository;

    @KafkaListener(topics = "transaction-service", groupId = "account-service-group")
    public void consumerAccountCreated(TransactionCreatedEvent transactionEvent) {
        Account fromAccount = accountRepository.findAccountByUserId(transactionEvent.getFromUserId());
        Account toAccount = accountRepository.findAccountByUserId(transactionEvent.getToUserId());

        fromAccount.setBalance(fromAccount.getBalance() - transactionEvent.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transactionEvent.getAmount());
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @KafkaListener(topics = "payment-service", groupId = "account-service-group",
            containerFactory = "paymentKafkaListenerContainerFactory")
    public void paymentConsumerAccountCreated(PaymentCreatedEvent paymentCreatedEvent) {

        if(paymentCreatedEvent.getPaymentType() != PaymentType.BANK_CARD){
            throw new RuntimeException("Only credit card payments are supported.");
        }

        Account account = accountRepository.findAccountByUserId(paymentCreatedEvent.getUserId());

        account.setBalance(account.getBalance() - paymentCreatedEvent.getAmount());
        accountRepository.save(account);
    }
}