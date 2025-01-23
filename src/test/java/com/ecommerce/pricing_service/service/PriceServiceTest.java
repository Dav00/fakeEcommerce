package com.ecommerce.pricing_service.service;

import com.ecommerce.pricing_service.entity.Price;
import com.ecommerce.pricing_service.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class PriceServiceTest {
    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;

    private Price priceOne;
    private Price priceTwo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        priceOne = new Price();
        priceOne.setBrandId(1);
        priceOne.setProductId(35455);
        priceOne.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        priceOne.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59, 59));
        priceOne.setPriceList(1);
        priceOne.setPriority(1);
        priceOne.setPrice(new BigDecimal("35.50"));
        priceOne.setCurrency("EUR");

        priceTwo = new Price();
        priceTwo.setBrandId(1);
        priceTwo.setProductId(35455);
        priceTwo.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        priceTwo.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
        priceTwo.setPriceList(2);
        priceTwo.setPriority(2);
        priceTwo.setPrice(new BigDecimal("25.45"));
        priceTwo.setCurrency("EUR");
    }

    @Test
    void testGetApplicablePrice_ShouldReturnPriceForGivenTimeOfHighestPriority() {
        when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                35455, 1, LocalDateTime.of(2020, 6, 14, 10, 0, 0), LocalDateTime.of(2020, 6, 14, 10, 0, 0)))
                .thenReturn(List.of(priceOne, priceTwo));

        Optional<Price> result = priceService.getApplicablePrice(35455, 1, LocalDateTime.of(2020, 6, 14, 10, 0, 0));

        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualTo(new BigDecimal("25.45"));
        assertThat(result.get().getPriority()).isEqualTo(2);
    }

    @Test
    void testGetApplicablePrice_ShouldReturnEmptyWhenNoPriceFound() {
        when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                35455, 1, LocalDateTime.of(2020, 6, 14, 10, 0, 0), LocalDateTime.of(2020, 6, 14, 10, 0, 0)))
                .thenReturn(List.of());

        Optional<Price> result = priceService.getApplicablePrice(35455, 1, LocalDateTime.of(2020, 6, 14, 10, 0, 0));

        assertThat(result).isEmpty();
    }
}