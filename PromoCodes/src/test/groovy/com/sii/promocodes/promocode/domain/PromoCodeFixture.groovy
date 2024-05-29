package com.sii.promocodes.promocode.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.promocode.dto.PromoCodeApi
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

class PromoCodeFixture {

    static final String CODE = "CODE1"
    static final String CODE_2 = "CODE2"
    static final int MAX_USAGES = 10
    static final BigDecimal AMOUNT = BigDecimal.valueOf(10)
    static final BigDecimal AMOUNT2 = BigDecimal.valueOf(15)
    static final Currency CURRENCY = Currency.EUR
    static final LocalDate EXPIRATION_DATE = LocalDate.now().plusDays(10)
    static final LocalDate EXPIRATION_DATE2 = LocalDate.now().plusDays(15)

    static class PromoCodeFixtureBuilder {
        String code = CODE
        int maxUsages = MAX_USAGES
        BigDecimal amount = AMOUNT
        Currency currency = CURRENCY
        LocalDate expirationDate = EXPIRATION_DATE


        @NotNull
        private LocalDate expirationDate;
        PromoCodeApi.CreatePromoCodeRequest buildCreatePromoCodeRequest() {

            new PromoCodeApi.CreatePromoCodeRequest(
                code,
                maxUsages,
                amount,
                currency,
                expirationDate
            )
        }

    }
    static PromoCodeApi.CreatePromoCodeRequest createPromoCodeRequest(@DelegatesTo(value = PromoCodeFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PromoCodeFixtureBuilder().tap(closure).buildCreatePromoCodeRequest()
    }

    static PromoCodeApi.PromoCode createPromoCodeDto() {
        new PromoCodeApi.PromoCode(
            CODE,
            MAX_USAGES,
                0,
                true,
            AMOUNT,
            CURRENCY,
            EXPIRATION_DATE
        )
    }

}
