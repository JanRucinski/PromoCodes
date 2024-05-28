package com.sii.promocodes.product.dto;

import com.sii.promocodes.commons.enums.Currency;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public final class ProductApi {

    @Value
    public static class Product {
        UUID id;
        String name;
        String description;
        BigDecimal price;
        Currency currency;
    }

    @Value
    public static class CreateProductRequest {
        String name;
        String description;
        BigDecimal price;
        Currency currency;
    }

    @Value
    public static class UpdateProductRequest {
        String name;
        String description;
        BigDecimal price;
        Currency currency;
    }
}
