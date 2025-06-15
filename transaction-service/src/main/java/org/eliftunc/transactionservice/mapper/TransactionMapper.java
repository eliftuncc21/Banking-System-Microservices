package org.eliftunc.transactionservice.mapper;

import org.eliftunc.events.TransactionCreatedEvent;
import org.eliftunc.transactionservice.dto.TransactionRequestDto;
import org.eliftunc.transactionservice.dto.TransactionResponseDto;
import org.eliftunc.transactionservice.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransaction(TransactionRequestDto transactionRequestDto);

    TransactionResponseDto toTransactionResponseDto(Transaction transaction);

    TransactionCreatedEvent toTransactionCreatedEvent(Transaction transaction);
}
