package com.sii.promocodes.purchase.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.sii.promocodes.common.InMemoryRepository
import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi
import com.sii.promocodes.product.domain.ProductFacade
import com.sii.promocodes.promocode.domain.PromoCodeFacade
import spock.lang.Specification

import static com.sii.promocodes.purchase.domain.PurchaseFixture.*
import static com.sii.promocodes.promocode.domain.PromoCodeFixture.*
import static com.sii.promocodes.product.domain.ProductFixture.*


class PurchaseBaseSpec extends Specification {

    InMemoryRepository purchaseRepository = new InMemoryPurchaseRepository()

    PurchaseConfiguration purchaseConfiguration
    PurchaseFacade purchaseFacade

    PriceCalculatorFacade priceCalculatorFacade
    ProductFacade productFacade;
    PromoCodeFacade promoCodeFacade;
    PurchaseReportHandler purchaseReportHandler;

    def setup() {
        priceCalculatorFacade = Mock() {
            calculatePrice(_) >> new PriceCalculatorApi.CalculatedPrice(
                    ORIGINAL_PRICE,
                    DISCOUNT,
                    Currency.EUR,
                    ""
            )
        }

        productFacade = Mock() {
            getProduct(_) >> createProductDto()
        }

        promoCodeFacade = Mock() {
            getPromoCode(_) >> createPromoCodeDto()
        }

        purchaseReportHandler = Mock() {
            generateSalesReport() >> createSalesReport()
        }

        setupPurchaseConfiguration()
        purchaseFacade = new PurchaseFacade(purchaseRepository, priceCalculatorFacade, productFacade, promoCodeFacade, purchaseReportHandler)
    }

    PurchaseConfiguration setupPurchaseConfiguration() {
        purchaseConfiguration = new PurchaseConfiguration(purchaseRepository, priceCalculatorFacade, productFacade, promoCodeFacade)
    }
}
