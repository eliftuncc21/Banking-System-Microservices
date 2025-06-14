package org.eliftunc.accountservice.repository;

import org.eliftunc.enums.ActiveStatus;

public interface AccountProjection {
    Long getUserId();
    Long getAccountId();
    String getAccountNumber();
    Double getBalance();
    ActiveStatus getActiveStatus();
}