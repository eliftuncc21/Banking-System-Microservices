package org.eliftunc.creditcardservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.client.UserServiceClient;
import org.eliftunc.creditcardservice.dto.CreditCardRequestDto;
import org.eliftunc.creditcardservice.dto.CreditCardResponseDto;
import org.eliftunc.creditcardservice.entity.CreditCard;
import org.eliftunc.creditcardservice.mapper.CreditCardMapper;
import org.eliftunc.creditcardservice.repository.CreditCardProjection;
import org.eliftunc.creditcardservice.repository.CreditCardRepository;
import org.eliftunc.creditcardservice.utils.Caches;
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
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;
    private final UserServiceClient userServiceClient;

    @CacheEvict(value = Caches.CARDS_CACHE, allEntries = true)
    public CreditCardResponseDto createCreditCard(CreditCardRequestDto creditCardRequestDto) {
        CreditCard card = creditCardMapper.toCreditCard(creditCardRequestDto);
        creditCardRepository.save(card);

        CreditCardResponseDto response = creditCardMapper.toCreditCardResponseDto(card);
        response.setUser(response.getUser());
        return response;
    }

    @Cacheable(value = Caches.CARDS_CACHE, key = "'all'")
    public Page<CreditCardResponseDto> getCreditCard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return creditCardRepository.findAll(pageable).map(card->{
            CreditCardResponseDto response = creditCardMapper.toCreditCardResponseDto(card);
            response.setUser(response.getUser());
            return response;
        });
    }

    @Cacheable(value = Caches.CARD_CACHE, key = "#cardId")
    public CreditCardResponseDto getCreditCardById(Long cardId){
        return creditCardRepository.findById(cardId).map(card -> {
            CreditCardResponseDto response = creditCardMapper.toCreditCardResponseDto(card);
            response.setUser(userServiceClient.getUserById(card.getUserId()));
            return response;
        }).orElse(null);
    }

    @CachePut(value = Caches.CARD_CACHE, key = "#cardId")
    @CacheEvict(value = Caches.CARDS_CACHE, allEntries = true)
    public CreditCardResponseDto updateCreditCard(CreditCardRequestDto creditCardRequestDto, Long cardId) {
        CreditCard card = creditCardRepository.findById(cardId).orElse(null);
        creditCardMapper.updateCreditCard(creditCardRequestDto, card);
        creditCardRepository.save(card);
        CreditCardResponseDto response = creditCardMapper.toCreditCardResponseDto(card);
        response.setUser(response.getUser());
        return response;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = Caches.CARDS_CACHE, allEntries = true),
                    @CacheEvict(value = Caches.CARD_CACHE, key = "#id")
            }
    )
    public void deleteCreditCardById(Long cardId) {
        if (creditCardRepository.existsById(cardId)){
            throw new RuntimeException("Credit card not found");
        }
        creditCardRepository.deleteById(cardId);
    }

    @Cacheable(value = Caches.CARD_CACHE, key = "'user_' + #userId")
    public CreditCardProjection getCreditCArdByUserId(Long userId){
        return creditCardRepository.findProjectionByUserId(userId);
    }
}
