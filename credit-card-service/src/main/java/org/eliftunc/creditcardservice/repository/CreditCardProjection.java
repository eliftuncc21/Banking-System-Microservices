package org.eliftunc.creditcardservice.repository;

import org.eliftunc.enums.ActiveStatus;

public interface CreditCardProjection {
    Long getCardId();
    String getCardNumber();
    Double getAvailableLimit();
    Double getCurrentDebt();
    ActiveStatus getActiveStatus();
}
