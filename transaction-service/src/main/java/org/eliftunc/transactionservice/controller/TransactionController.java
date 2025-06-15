package org.eliftunc.transactionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eliftunc.transactionservice.dto.TransactionRequestDto;
import org.eliftunc.transactionservice.dto.TransactionResponseDto;
import org.eliftunc.transactionservice.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public void transaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto){
        transactionService.transaction(transactionRequestDto);
    }

    @GetMapping("/transaction-list")
    public Page<TransactionResponseDto> getTransactionList(@RequestParam int page, @RequestParam int size){
        return transactionService.getTransaction(page, size);
    }

    @GetMapping("transaction/{id}")
    public TransactionResponseDto getTransactionById(@PathVariable("id") Long id){
        return transactionService.getTransactionById(id);
    }
}