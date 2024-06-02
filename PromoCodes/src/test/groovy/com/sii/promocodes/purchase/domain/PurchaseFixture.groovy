package com.sii.promocodes.purchase.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.product.dto.ProductApi
import com.sii.promocodes.purchase.dto.PurchaseApi
import io.swagger.v3.oas.annotations.media.Schema

import java.time.LocalDate

import static com.sii.promocodes.commons.enums.Currency.EUR

class PurchaseFixture {

    static final UUID PURCHASE_ID = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7d")
    static final UUID EUR_PRODUCT_ID = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7e")
    static final String EUR_PRODUCT_NAME = "product EUR"
    static final String PROMO_CODE = "promoCode"
    static final BigDecimal ORIGINAL_PRICE = BigDecimal.valueOf(100)
    static final BigDecimal DISCOUNT = BigDecimal.valueOf(10)

    static class PurchaseFixtureBuilder {

        UUID id = PURCHASE_ID
        UUID productId = EUR_PRODUCT_ID
        String productName = EUR_PRODUCT_NAME
        String promoCode = PROMO_CODE
        BigDecimal price = ORIGINAL_PRICE
        BigDecimal discount = DISCOUNT
        Currency currency = EUR

        PurchaseApi.CreatePurchaseRequest buildCreatePurchaseRequest() {

            new PurchaseApi.CreatePurchaseRequest(
                    productId,
                    promoCode
            )
        }

        PurchaseApi.Purchase buildPurchaseDto() {
            new PurchaseApi.Purchase(
                    productId,
                    productName,
                    price,
                    discount,
                    currency,
                    LocalDate.now()
            )
        }


    }

    static PurchaseApi.CreatePurchaseRequest createPurchaseRequest(@DelegatesTo(value = PurchaseFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PurchaseFixtureBuilder().tap(closure).buildCreatePurchaseRequest()
    }

    static PurchaseApi.Purchase createPurchaseDto(@DelegatesTo(value = PurchaseFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new PurchaseFixtureBuilder().tap(closure).buildPurchaseDto()
    }

    static PurchaseApi.Purchase createPurchase() {
        createPurchaseDto({
        })
    }

}
