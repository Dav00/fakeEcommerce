package com.ecommerce.pricing_service.service;

import com.ecommerce.pricing_service.entity.Price;
import com.ecommerce.pricing_service.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<Price> getApplicablePrice(int productId, int brandId, LocalDateTime applicationDate) {
        return priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        productId, brandId, applicationDate, applicationDate)
                .stream()
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()));
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }
}
