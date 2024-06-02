package com.sii.promocodes.pricecalculator.domain

import com.sii.promocodes.product.domain.ProductFacade
import com.sii.promocodes.promocode.domain.PromoCodeFacade
import spock.lang.Specification

import static com.sii.promocodes.product.domain.ProductFixture.*
import static com.sii.promocodes.promocode.domain.PromoCodeFixture.*

class PriceCalculatorBaseSpec extends Specification {

    PriceCalculatorConfiguration priceCalculatorConfiguration
    PriceCalculatorFacade priceCalculatorFacade

    ProductFacade productFacade
    PromoCodeFacade promoCodeFacade

    def setup() {
        setupPriceCalculatorConfiguration()

        productFacade = Mock(){
            getProduct(EUR_PRODUCT_ID) >> createProductDto()
            getProduct(USD_PRODUCT_ID) >> createProduct2()
        }
        promoCodeFacade = Mock(){
            getPromoCode(EUR_CODE_ACTIVE) >> createPromoCodeDto()
            getPromoCode(EUR_CODE_EXPIRED) >> createExpiredPromoCodeDto()
            getPromoCode(EUR_CODE_DEPLETED) >> createDepletedPromoCodeDto()
            getPromoCode(EUR_CODE_ACTIVE_BIG) >> createBigPromoCodeDto()
            getPromoCode(EUR_CODE_ACTIVE_50_PERCENT) >> createPromoCodeWith50PercentDiscount()
        }

        priceCalculatorFacade = new PriceCalculatorFacade(productFacade, promoCodeFacade)
    }

    PriceCalculatorConfiguration setupPriceCalculatorConfiguration() {
        priceCalculatorConfiguration = new PriceCalculatorConfiguration(promoCodeFacade, productFacade)
    }

}
