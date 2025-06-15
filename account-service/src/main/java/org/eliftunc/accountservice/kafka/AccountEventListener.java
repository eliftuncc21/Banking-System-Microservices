package org.eliftunc.accountservice.kafka;

import lombok.RequiredArgsConstructor;
import org.eliftunc.accountservice.entity.Account;
import org.eliftunc.accountservice.repository.AccountRepository;
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
}