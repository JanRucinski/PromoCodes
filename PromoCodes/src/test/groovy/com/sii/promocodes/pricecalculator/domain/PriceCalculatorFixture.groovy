package com.sii.promocodes.pricecalculator.domain

import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi
import com.sii.promocodes.commons.enums.Currency

import static com.sii.promocodes.commons.enums.Currency.EUR
import static com.sii.promocodes.product.domain.ProductFixture.EUR_PRODUCT_ID
import static com.sii.promocodes.promocode.domain.PromoCodeFixture.EUR_CODE_ACTIVE

class PriceCalculatorFixture {

    static final BigDecimal BASE_PRICE = BigDecimal.valueOf(200)
    static final BigDecimal CALCULATED_PRICE_FLAT_10 = BigDecimal.valueOf(190)
    static final BigDecimal CALCULATED_PRICE_PERCENTAGE_50 = BigDecimal.valueOf(100)
    static final String CURRENCIEC_MISMATCH_WARNING = "The currencies do not match, the price was not adjusted"
    static final String PROMOCODE_EXPIRED_WARNING = "The PromoCode is Expired, the price was not adjusted"
    static final String PROMOCODE_DEPLETED_WARNING = "The PromoCode is Depleted, the price was not adjusted"

    static class PriceCalculatorFixtureBuilder {
        UUID productId = EUR_PRODUCT_ID
        String promoCode = EUR_CODE_ACTIVE

        BigDecimal calculatedPrice = CALCULATED_PRICE_FLAT_10
        BigDecimal discountAmount = BASE_PRICE.subtract(CALCULATED_PRICE_FLAT_10)
        Currency currency = EUR
        String warning = ""

        PriceCalculatorApi.CalculatePriceRequest buildCalculatePriceRequest () {

            new PriceCalculatorApi.CalculatePriceRequest(
                productId,
                promoCode,
            )
        }

        PriceCalculatorApi.CalculatedPrice buildCalculatedPrice () {
            new PriceCalculatorApi.CalculatedPrice(

                    calculatedPrice,
                    discountAmount,
                    currency,
                    warning
            )
        }

    }

    static PriceCalculatorApi.CalculatePriceRequest createCalculatePriceRequest(@DelegatesTo(value = PriceCalculatorFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PriceCalculatorFixtureBuilder().tap(closure).buildCalculatePriceRequest()
    }

    static PriceCalculatorApi.CalculatedPrice createCalculatedPrice(@DelegatesTo(value = PriceCalculatorFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PriceCalculatorFixtureBuilder().tap(closure).buildCalculatedPrice()
    }

}
