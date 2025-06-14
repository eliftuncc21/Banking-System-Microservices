package org.eliftunc.creditcardservice.repository;

import org.eliftunc.creditcardservice.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findCreditCardByUserId(Long userId);
    CreditCardProjection findProjectionByUserId(Long userId);
}