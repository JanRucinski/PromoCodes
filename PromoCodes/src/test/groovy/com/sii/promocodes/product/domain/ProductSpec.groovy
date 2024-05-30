package com.sii.promocodes.product.domain

import com.sii.promocodes.commons.db.RandomStringGenerator
import static com.sii.promocodes.product.domain.ProductFixture.*

class ProductSpec extends ProductBaseSpec{

        def "should create a product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(EUR_PRODUCT_ID)

            when:
            var response = productFacade.createProduct(createProductRequest())

            then:
            response == createProductDto()
        }

        def "should get all products"() {
            given:
            RandomStringGenerator.setFixedUuidValue(EUR_PRODUCT_ID)
            productFacade.createProduct(createProductRequest())
            RandomStringGenerator.setFixedUuidValue(USD_PRODUCT_ID)
            productFacade.createProduct(createProductRequest({
                name = USD_PRODUCT_NAME
            }))

            when:
            var response = productFacade.getAllProducts()

            then:
            response.size() == 2
            response.stream().anyMatch { it.id == EUR_PRODUCT_ID }
            response.stream().anyMatch { it.id == USD_PRODUCT_ID }
            response.stream().anyMatch { it.name == EUR_PRODUCT_NAME }
            response.stream().anyMatch { it.name == USD_PRODUCT_NAME }
        }

        def "should update product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(EUR_PRODUCT_ID)
            productFacade.createProduct(createProductRequest())

            when:
            var response = productFacade.getProduct(EUR_PRODUCT_ID)

            then:
            response == createProductDto()
        }

        def "should delete product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(EUR_PRODUCT_ID)
            productFacade.createProduct(createProductRequest())

            when:
            productFacade.deleteProduct(EUR_PRODUCT_ID)

            then:
            productFacade.getAllProducts().size() == 0
        }
}
