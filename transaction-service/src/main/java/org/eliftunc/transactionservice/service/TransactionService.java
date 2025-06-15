package org.eliftunc.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.client.AccountServiceClient;
import org.eliftunc.dto.AccountDto;
import org.eliftunc.events.TransactionCreatedEvent;
import org.eliftunc.transactionservice.dto.TransactionRequestDto;
import org.eliftunc.transactionservice.dto.TransactionResponseDto;
import org.eliftunc.transactionservice.entity.Transaction;
import org.eliftunc.transactionservice.mapper.TransactionMapper;
import org.eliftunc.transactionservice.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountServiceClient accountServiceClient;
    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;

    public void transaction(TransactionRequestDto transactionRequestDto) {
        AccountDto fromAccount = accountServiceClient.getAccountByUserId(transactionRequestDto.getFromUserId());

        if(fromAccount.getBalance() < transactionRequestDto.getAmount()){
            throw new RuntimeException("Insufficient balance for the transaction.");
        }

        Transaction transaction = transactionMapper.toTransacton(transactionRequestDto);
        transactionRepository.save(transaction);

        TransactionCreatedEvent event = transactionMapper.toTransactionCreatedEvent(transaction);
        kafkaTemplate.send("transaction-service", event);
    }

    public Page<TransactionResponseDto> getTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable).map(this::mapToTransactionResponseDto);
    }

    private TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {
        TransactionResponseDto transactionResponseDto = transactionMapper.toTransactionResponseDto(transaction);
        transactionResponseDto.setFromAccount(accountServiceClient.getAccountByUserId(transaction.getFromUserId()));
        transactionResponseDto.setToAccount(accountServiceClient.getAccountByUserId(transaction.getToUserId()));
        return transactionResponseDto;
    }

    public TransactionResponseDto getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::mapToTransactionResponseDto)
                .orElse(null);
    }
}