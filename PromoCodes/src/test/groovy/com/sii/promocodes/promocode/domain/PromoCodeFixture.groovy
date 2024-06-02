package com.sii.promocodes.promocode.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.commons.enums.DiscountType
import com.sii.promocodes.commons.enums.Usability
import com.sii.promocodes.promocode.dto.PromoCodeApi
import com.sii.promocodes.purchase.dto.PurchaseApi

import java.time.LocalDate

import static com.sii.promocodes.commons.enums.Currency.EUR
import static com.sii.promocodes.commons.enums.DiscountType.FLAT
import static com.sii.promocodes.commons.enums.DiscountType.PERCENTAGE
import static com.sii.promocodes.commons.enums.Usability.*

class PromoCodeFixture {

    static final String EUR_CODE_ACTIVE = "CODEACTIVE"
    static final String EUR_CODE_EXPIRED = "CODEEXPIRED"
    static final String EUR_CODE_DEPLETED = "CODEDEPLETED"
    static final String EUR_CODE_ACTIVE_BIG = "CODEACTIVEBIG"
    static final String EUR_CODE_ACTIVE_50_PERCENT = "CODEACTIVE50PERCENT"
    static final int MAX_USAGES = 10
    static final BigDecimal AMOUNT_10 = BigDecimal.valueOf(10.00)
    static final BigDecimal AMOUNT_50 = BigDecimal.valueOf(50.00)
    static final BigDecimal AMOUNT_BIG = BigDecimal.valueOf(999999999.00)
    static final LocalDate EXPIRATION_DATE_ACTIVE_CODE = LocalDate.now().plusDays(10)
    static final LocalDate EXPIRATION_DATE_EXPIRED_CODE = LocalDate.now().minusDays(10)

    static class PromoCodeFixtureBuilder {
        String code = EUR_CODE_ACTIVE
        int maxUsages = MAX_USAGES
        BigDecimal amount = AMOUNT_10.setScale(2)
        Currency currency = EUR
        LocalDate expirationDate = EXPIRATION_DATE_ACTIVE_CODE
        int currentUsages = 0
        Usability usability = ACTIVE
        DiscountType discountType = FLAT

        PromoCodeApi.CreatePromoCodeRequest buildCreatePromoCodeRequest() {

            new PromoCodeApi.CreatePromoCodeRequest(
                    code,
                    maxUsages,
                    amount,
                    currency,
                    expirationDate,
                    discountType
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
                    expirationDate,
                    discountType
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

    static PromoCodeApi.PromoCode createPromoCodeWith50PercentDiscount() {
        createPromoCodeDto({
            code = EUR_CODE_ACTIVE_50_PERCENT
            amount = AMOUNT_50
            discountType = PERCENTAGE
        })
    }

    static PromoCodeApi.PromoCode createBigPromoCodeDto() {
        createPromoCodeDto({
            amount = AMOUNT_BIG
        })
    }

}
