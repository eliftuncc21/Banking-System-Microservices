package org.eliftunc.client;

import org.eliftunc.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", url = "http://localhost:8082")
public interface AccountServiceClient {
    @GetMapping("/api/account/account/{id}")
    AccountDto getAccountByUserId(@PathVariable(name = "id") Long id);

    @GetMapping("/api/account/{id}")
    AccountDto getAccountById(@PathVariable(name = "id") Long id);
}