package com.sii.promocodes.pricecalculator.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sii.promocodes.commons.enums.Currency;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class PriceCalculatorApi {

    @Value
    public static class CalculatePriceRequest {
        UUID productId;
        String promoCode;
    }

    @Value
    public static class CalculatedPrice {

        BigDecimal calculatedPrice;

        Currency currency;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String warning;
    }

}
