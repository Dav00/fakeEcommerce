package com.ecommerce.pricing_service.repository;

import com.ecommerce.pricing_service.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            int productId, int brandId, LocalDateTime startDateInclusive, LocalDateTime endDateInclusive);
}
