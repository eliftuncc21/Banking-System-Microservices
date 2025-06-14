package org.eliftunc.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.accountservice.dto.AccountRequestDto;
import org.eliftunc.accountservice.dto.AccountResponseDto;
import org.eliftunc.accountservice.entity.Account;
import org.eliftunc.accountservice.mapper.AccountMapper;
import org.eliftunc.accountservice.repository.AccountProjection;
import org.eliftunc.accountservice.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto){
        Account account = accountMapper.toAccount(accountRequestDto);
        accountRepository.save(account);

        return accountMapper.toAccountResponseDto(account);
    }

    public Page<AccountResponseDto> getAccount(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable)
                .map(accountMapper::toAccountResponseDto);
    }

    public AccountResponseDto getAccountById(Long accountId){
        return accountRepository.findById(accountId)
                .map(accountMapper::toAccountResponseDto)
                .orElse(null);
    }

    public AccountResponseDto updateAccount(Long id, AccountRequestDto accountRequestDto){
        Account account = accountRepository.findById(id).orElse(null);
        accountMapper.updateAccount(accountRequestDto, account);
        accountRepository.save(account);
        return accountMapper.toAccountResponseDto(account);
    }

    public void deleteAccount(Long id){
        if(!accountRepository.existsById(id)){
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    public AccountProjection getAccountByUserId(Long userId){
        return accountRepository.findProjectionByUserId(userId);
    }
}
