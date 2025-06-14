package org.eliftunc.creditcardservice.mapper;

import org.eliftunc.creditcardservice.dto.CreditCardRequestDto;
import org.eliftunc.creditcardservice.dto.CreditCardResponseDto;
import org.eliftunc.creditcardservice.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    CreditCard toCreditCard(CreditCardRequestDto creditCardRequestDto);

    CreditCardResponseDto toCreditCardResponseDto(CreditCard creditCard);

    @Mapping(target = "cardId", ignore = true)
    void updateCreditCard(CreditCardRequestDto creditCardRequestDto, @MappingTarget CreditCard creditCard);
}
