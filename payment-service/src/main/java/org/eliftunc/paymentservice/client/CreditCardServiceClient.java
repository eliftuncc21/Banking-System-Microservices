package org.eliftunc.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "credit-card-service", url = "http://localhost:8083")
public interface CreditCardServiceClient {
    @GetMapping("/api/credit-card/credit-card/{id}")
    CreditCard getCreditCardByUserId(@PathVariable("id") Long id);
}
