package com.ecommerce.pricing_service.controller;

import com.ecommerce.pricing_service.entity.Price;
import com.ecommerce.pricing_service.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity getPrice (@RequestParam int productId,
                                    @RequestParam int brandId,
                                    @RequestParam LocalDate applicationDate) {
        LocalDateTime applicationDateTime = LocalDateTime.from(applicationDate);
        return priceService.getApplicablePrice(productId, brandId, applicationDateTime)
                .map(price -> ResponseEntity.ok(price))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Price>> getAllPrices() {
        List<Price> prices = priceService.getAllPrices();
        return ResponseEntity.ok(prices);
    }
}
