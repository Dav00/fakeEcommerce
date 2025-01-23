package com.ecommerce.pricing_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.assertThat;

//Integration Tests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {
	String localUrl = "http://localhost:8080/api/prices?";

	@Autowired
	private TestRestTemplate restTemplate;

	private LocalDateTime parseDateTime(String dateTimeStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return LocalDateTime.parse(dateTimeStr, formatter);
	}

	@Test
	void testPriceAt1000On14thJune() {
		String url = localUrl + "productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("35.50");
	}

	@Test
	void testPriceAt1600On14thJune() {
		String url = localUrl + "productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("25.45");
	}

	@Test
	void testPriceAt2100On14thJune() {
		String url = localUrl + "productId=35455&brandId=1&applicationDate=2020-06-14T21:00:00";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("35.50");
	}

	@Test
	void testPriceAt1000On15thJune() {
		String url = localUrl + "productId=35455&brandId=1&applicationDate=2020-06-15T10:00:00";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("30.50");
	}

	@Test
	void testPriceAt2100On16thJune() {
		String url = localUrl + "productId=35455&brandId=1&applicationDate=2020-06-16T21:00:00";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("38.95");
	}
}

