package com.sii.promocodes.product.domain

import com.sii.promocodes.commons.enums.Currency
import com.sii.promocodes.product.dto.ProductApi

import static com.sii.promocodes.commons.enums.Currency.EUR
import static com.sii.promocodes.commons.enums.Currency.USD

class ProductFixture {

    static final UUID EUR_PRODUCT_ID = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7e")
    static final UUID USD_PRODUCT_ID = UUID.fromString("f1f8b3e4-3b3b-4b7e-8b7e-3b3b4b7e8b7f")
    static final String EUR_PRODUCT_NAME = "product EUR"
    static final String USD_PRODUCT_NAME = "product USD"
    static final String DESCRIPTION = "product description"
    static final BigDecimal PRICE = BigDecimal.valueOf(100)

    static class ProductFixtureBuilder {
        UUID id = EUR_PRODUCT_ID
        String name = EUR_PRODUCT_NAME
        String description = DESCRIPTION
        BigDecimal price = PRICE
        Currency currency = EUR

        ProductApi.CreateProductRequest buildCreateProductRequest() {

            new ProductApi.CreateProductRequest(
                    name,
                    description,
                    price,
                    currency
            )
        }

        ProductApi.Product buildProductDto() {
            new ProductApi.Product(
                    id,
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

    static ProductApi.Product createProductDto(@DelegatesTo(value = ProductFixtureBuilder, strategy = Closure.DELEGATE_FIRST) closure = {}) {
        new ProductFixtureBuilder().tap(closure).buildProductDto()
    }

    static ProductApi.Product createProduct2() {
        createProductDto({
            id = USD_PRODUCT_ID
            name = USD_PRODUCT_NAME
            currency = USD
        })
    }

}
