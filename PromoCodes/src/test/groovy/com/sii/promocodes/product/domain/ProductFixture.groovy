package com.sii.promocodes.product.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.product.dto.ProductApi

import static com.sii.promocodes.commons.enums.Currency.*

class ProductFixture {

    static final UUID ID = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7e")
    static final UUID ID_2 = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7f")
    static final String NAME = "product1"
    static final String NAME_2 = "product2"
    static final String DESCRIPTION = "product1 description"
    static final BigDecimal PRICE = BigDecimal.valueOf(100)
    static final Currency CURRENCY = EUR

    static class ProductFixtureBuilder {
        String name = NAME
        String description = DESCRIPTION
        BigDecimal price = PRICE
        Currency currency = CURRENCY

        ProductApi.CreateProductRequest buildCreateProductRequest() {

            new ProductApi.CreateProductRequest(
                name,
                description,
                price,
                currency
            )
        }

    }
    static ProductApi.CreateProductRequest createProductRequest(@DelegatesTo(value = ProductFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new ProductFixtureBuilder().tap(closure).buildCreateProductRequest()
    }

    static ProductApi.Product createProductDto() {
        new ProductApi.Product(
            ID,
            NAME,
            DESCRIPTION,
            PRICE,
            CURRENCY
        )
    }

}
