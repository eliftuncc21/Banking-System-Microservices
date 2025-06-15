package org.eliftunc.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.accountservice.dto.AccountRequestDto;
import org.eliftunc.accountservice.dto.AccountResponseDto;
import org.eliftunc.accountservice.entity.Account;
import org.eliftunc.accountservice.mapper.AccountMapper;
import org.eliftunc.accountservice.repository.AccountProjection;
import org.eliftunc.accountservice.repository.AccountRepository;
import org.eliftunc.accountservice.utils.Caches;
import org.eliftunc.client.UserServiceClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserServiceClient userServiceClient;

    @CacheEvict(value = Caches.ACCOUNTS_CACHE, allEntries = true)
    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto){
        Account account = accountMapper.toAccount(accountRequestDto);
        accountRepository.save(account);
        AccountResponseDto response = accountMapper.toAccountResponseDto(account);
        response.setUser(userServiceClient.getUserById(account.getUserId()));
        return response;
    }

    @Cacheable(value = Caches.ACCOUNTS_CACHE, key = "'all'")
    public Page<AccountResponseDto> getAccount(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable).map(account -> {
            AccountResponseDto response = accountMapper.toAccountResponseDto(account);
            response.setUser(userServiceClient.getUserById(account.getUserId()));
            return response;
        });
    }

    @Cacheable(value = Caches.ACCOUNT_CACHE, key = "#accountId")
    public AccountResponseDto getAccountById(Long accountId){
        return accountRepository.findById(accountId).map(account -> {
                    AccountResponseDto accountResponseDto = accountMapper.toAccountResponseDto(account);
                    accountResponseDto.setUser(userServiceClient.getUserById(account.getUserId()));
                    return accountResponseDto;
                }).orElse(null);
    }

    @CachePut(value = Caches.ACCOUNT_CACHE, key = "#id")
    @CacheEvict(value = Caches.ACCOUNTS_CACHE, allEntries = true)
    public AccountResponseDto updateAccount(Long id, AccountRequestDto accountRequestDto){
        Account account = accountRepository.findById(id).orElse(null);
        accountMapper.updateAccount(accountRequestDto, account);
        accountRepository.save(account);
        AccountResponseDto response = accountMapper.toAccountResponseDto(account);
        response.setUser(userServiceClient.getUserById(account.getUserId()));
        return response;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = Caches.ACCOUNT_CACHE, key = "#id"),
                    @CacheEvict(value = Caches.ACCOUNTS_CACHE, allEntries = true)
            }
    )
    public void deleteAccount(Long id){
        if(!accountRepository.existsById(id)){
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    @Cacheable(value = Caches.ACCOUNT_CACHE, key= "'user_' + #userId")
    public AccountProjection getAccountByUserId(Long userId){
        return accountRepository.findProjectionByUserId(userId);
    }
}
