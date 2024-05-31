package com.sii.promocodes.promocode.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.commons.enums.Usability
import com.sii.promocodes.promocode.dto.PromoCodeApi

import java.time.LocalDate

import static com.sii.promocodes.commons.enums.Usability.*

class PromoCodeFixture {

    static final String EUR_CODE_ACTIVE = "CODE ACTIVE"
    static final String EUR_CODE_EXPIRED = "CODE EXPIRED"
    static final String EUR_CODE_DEPLETED = "CODE DEPLETED"
    static final String EUR_CODE_ACTIVE_999 = "CODE ACTIVE 999"
    static final int MAX_USAGES = 10
    static final BigDecimal AMOUNT_10 = BigDecimal.valueOf(10)
    static final BigDecimal AMOUNT_15 = BigDecimal.valueOf(15)
    static final BigDecimal AMOUNT_999 = BigDecimal.valueOf(999)
    static final Currency CURRENCY = Currency.EUR
    static final LocalDate EXPIRATION_DATE_ACTIVE_CODE = LocalDate.now().plusDays(10)
    static final LocalDate EXPIRATION_DATE_EXPIRED_CODE = LocalDate.now().minusDays(10)

    static class PromoCodeFixtureBuilder {
        String code = EUR_CODE_ACTIVE
        int maxUsages = MAX_USAGES
        BigDecimal amount = AMOUNT_10
        Currency currency = CURRENCY
        LocalDate expirationDate = EXPIRATION_DATE_ACTIVE_CODE
        int currentUsages = 0
        Usability usability = ACTIVE

        PromoCodeApi.CreatePromoCodeRequest buildCreatePromoCodeRequest() {

            new PromoCodeApi.CreatePromoCodeRequest(
                    code,
                    maxUsages,
                    amount,
                    currency,
                    expirationDate
            )
        }

        PromoCodeApi.PromoCode buildPromoCodeDto() {
            new PromoCodeApi.PromoCode(
                    code,
                    maxUsages,
                    currentUsages,
                    usability,
                    amount,
                    currency,
                    expirationDate
            )
        }

    }

    static PromoCodeApi.CreatePromoCodeRequest createPromoCodeRequest(@DelegatesTo(value = PromoCodeFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PromoCodeFixtureBuilder().tap(closure).buildCreatePromoCodeRequest()
    }

    static PromoCodeApi.PromoCode createPromoCodeDto(@DelegatesTo(value = PromoCodeFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PromoCodeFixtureBuilder().tap(closure).buildPromoCodeDto()
    }

    static PromoCodeApi.PromoCode createExpiredPromoCodeDto() {
        createPromoCodeDto({
            code = EUR_CODE_EXPIRED
            usability = EXPIRED
            expirationDate = EXPIRATION_DATE_EXPIRED_CODE
        })
    }

    static PromoCodeApi.PromoCode createDepletedPromoCodeDto() {
        createPromoCodeDto({
            code = EUR_CODE_DEPLETED
            usability = DEPLETED
            currentUsages = MAX_USAGES
        })
    }

    static PromoCodeApi.PromoCode createBigPromoCodeDto() {
        createPromoCodeDto({
            amount = AMOUNT_999
        })
    }

}
