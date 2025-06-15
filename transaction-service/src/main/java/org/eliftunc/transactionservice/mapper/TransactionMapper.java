package org.eliftunc.transactionservice.mapper;

import org.eliftunc.transactionservice.dto.TransactionRequestDto;
import org.eliftunc.transactionservice.dto.TransactionResponseDto;
import org.eliftunc.transactionservice.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransacton(TransactionRequestDto transactionRequestDto);

    TransactionResponseDto toTransactionResponseDto(Transaction transaction);
}
