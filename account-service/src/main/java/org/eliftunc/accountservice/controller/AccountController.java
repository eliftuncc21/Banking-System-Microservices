package org.eliftunc.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eliftunc.accountservice.dto.AccountRequestDto;
import org.eliftunc.accountservice.dto.AccountResponseDto;
import org.eliftunc.accountservice.repository.AccountProjection;
import org.eliftunc.accountservice.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping( "/create-account")
    public AccountResponseDto createAccount(@RequestBody @Valid AccountRequestDto accountRequestDto){
        return accountService.createAccount(accountRequestDto);
    }

    @GetMapping("/list-account")
    public Page<AccountResponseDto> getAllAccount(@RequestParam int page, @RequestParam int size){
        return accountService.getAccount(page, size);
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @PutMapping("/update-account/{id}")
    public AccountResponseDto updateAccount(@PathVariable Long id, @RequestBody @Valid AccountRequestDto accountRequestDto){
        return accountService.updateAccount(id, accountRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
    }

    @GetMapping("/account/{id}")
    public AccountProjection getAccountByUserId(@PathVariable Long id){
        return accountService.getAccountByUserId(id);
    }

}
