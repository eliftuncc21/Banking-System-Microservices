package org.eliftunc.creditcardservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.creditcardservice.dto.CreditCardRequestDto;
import org.eliftunc.creditcardservice.dto.CreditCardResponseDto;
import org.eliftunc.creditcardservice.entity.CreditCard;
import org.eliftunc.creditcardservice.mapper.CreditCardMapper;
import org.eliftunc.creditcardservice.repository.CreditCardProjection;
import org.eliftunc.creditcardservice.repository.CreditCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;

    public CreditCardResponseDto createCreditCard(CreditCardRequestDto creditCardRequestDto) {
        CreditCard card = creditCardMapper.toCreditCard(creditCardRequestDto);
        creditCardRepository.save(card);

        return creditCardMapper.toCreditCardResponseDto(card);
    }

    public Page<CreditCardResponseDto> getCreditCard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return creditCardRepository.findAll(pageable)
                .map(creditCardMapper::toCreditCardResponseDto);
    }

    public CreditCardResponseDto getCreditCardById(Long cardId){
        return creditCardRepository.findById(cardId)
                .map(creditCardMapper::toCreditCardResponseDto)
                .orElse(null);
    }

    public CreditCardResponseDto updateCreditCard(CreditCardRequestDto creditCardRequestDto, Long cardId) {
        CreditCard card = creditCardRepository.findById(cardId).orElse(null);
        creditCardMapper.updateCreditCard(creditCardRequestDto, card);
        creditCardRepository.save(card);
        return creditCardMapper.toCreditCardResponseDto(card);
    }

    public void deleteCreditCardById(Long cardId) {
        if (creditCardRepository.existsById(cardId)){
            throw new RuntimeException("Credit card not found");
        }
        creditCardRepository.deleteById(cardId);
    }

    public CreditCardProjection getCreditCArdByUserId(Long userId){
        return creditCardRepository.findProjectionByUserId(userId);
    }
}
