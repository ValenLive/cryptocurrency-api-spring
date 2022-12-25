package com.oril.cryptocurrencyspring.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "cryptocurrencies")
public record Data(
        String timestamp,
        String pair,
        String low,
        String high,
        String last,
        String volume,
        String volume30d,
        String priceChange,
        String priceChangePercentage,
        BigDecimal bid,
        BigDecimal ask) {

}
