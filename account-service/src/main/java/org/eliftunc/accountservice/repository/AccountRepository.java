package org.eliftunc.accountservice.repository;

import org.eliftunc.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUserId(Long userId);
    AccountProjection findProjectionByUserId(Long userId);
}