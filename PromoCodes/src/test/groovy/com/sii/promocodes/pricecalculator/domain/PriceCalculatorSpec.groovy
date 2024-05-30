package com.sii.promocodes.pricecalculator.domain

import static com.sii.promocodes.pricecalculator.domain.PriceCalculatorFixture.*
import static com.sii.promocodes.product.domain.ProductFixture.*
import static com.sii.promocodes.promocode.domain.PromoCodeFixture.*


class PriceCalculatorSpec extends PriceCalculatorBaseSpec {

    def "should calculate price with a matching promo code"() {
        when:
        var response = priceCalculatorFacade.calculatePrice(createCalculatePriceRequest(
                {
                    productId = EUR_PRODUCT_ID
                    promoCode = EUR_CODE_ACTIVE
                }
        ))

        then:
        response.calculatedPrice == CALCULATED_PRICE
    }

    def "should return base price and a warning when calculating with an expired promo code" () {
        when:
        var response = priceCalculatorFacade.calculatePrice(createCalculatePriceRequest(
                {
                    productId = EUR_PRODUCT_ID
                    promoCode = EUR_CODE_EXPIRED
                }
        ))

        then:
        response.calculatedPrice == BASE_PRICE
        response.warning == PROMOCODE_EXPIRED_WARNING
    }

    def "should return base price and a warning when calculating with an depleted promo code" () {
        when:
        var response = priceCalculatorFacade.calculatePrice(createCalculatePriceRequest(
                {
                    productId = EUR_PRODUCT_ID
                    promoCode = EUR_CODE_DEPLETED
                }
        ))

        then:
        response.calculatedPrice == BASE_PRICE
        response.warning == PROMOCODE_DEPLETED_WARNING
    }

    def "should return base price and a warning when calculating different currencies" () {
        when:
        var response = priceCalculatorFacade.calculatePrice(createCalculatePriceRequest(
                {
                    productId = USD_PRODUCT_ID
                    promoCode = EUR_CODE_ACTIVE
                }
        ))

        then:
        response.calculatedPrice == BASE_PRICE
        response.warning == CURRENCIEC_MISMATCH_WARNING
    }
}