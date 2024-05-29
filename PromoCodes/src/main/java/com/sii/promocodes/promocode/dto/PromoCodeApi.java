package com.sii.promocodes.promocode.dto;

import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.commons.enums.Usability;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class PromoCodeApi {

        @Value
        public static class PromoCode {
            String code;
            int maxUsages;
            int currentUsages;
            Usability usability;
            BigDecimal amount;
            Currency currency;
            LocalDate expirationDate;
        }

        @Value
        public static class CreatePromoCodeRequest {
            String code;
            int maxUsages;
            BigDecimal amount;
            Currency currency;
            LocalDate expirationDate;

        }

        @Value
        public static class UpdatePromoCodeRequest {
            int maxUsages;
            BigDecimal amount;
            Currency currency;
            LocalDate expirationDate;
        }
}
