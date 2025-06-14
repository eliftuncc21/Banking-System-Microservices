package org.eliftunc.creditcardservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eliftunc.creditcardservice.dto.CreditCardRequestDto;
import org.eliftunc.creditcardservice.dto.CreditCardResponseDto;
import org.eliftunc.creditcardservice.repository.CreditCardProjection;
import org.eliftunc.creditcardservice.service.CreditCardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit-card")
public class CreditCardController {
    private final CreditCardService creditCardService;

    @PostMapping("/save-card")
    public CreditCardResponseDto addCreditCard(@RequestBody @Valid CreditCardRequestDto creditCardRequestDto){
        return creditCardService.createCreditCard(creditCardRequestDto);
    }

    @GetMapping("/list-card")
    public Page<CreditCardResponseDto> getCreditCardList(@RequestParam int page, @RequestParam int size){
        return creditCardService.getCreditCard(page, size);
    }

    @GetMapping("/list-card/{id}")
    public CreditCardResponseDto getCreditCardById(@PathVariable Long id){
        return creditCardService.getCreditCardById(id);
    }

    @PutMapping("/update-card/{id}")
    public CreditCardResponseDto updateCard(@PathVariable Long id, @RequestBody @Valid CreditCardRequestDto creditCardRequestDto){
        return creditCardService.updateCreditCard(creditCardRequestDto, id);
    }

    @DeleteMapping("/delete-card/{id}")
    public void deleteCard(@PathVariable Long id){
        creditCardService.deleteCreditCardById(id);
    }

    @GetMapping("/credit-card/{id}")
    public CreditCardProjection getCreditCardByUserId(@PathVariable Long id){
        return creditCardService.getCreditCArdByUserId(id);
    }
}
