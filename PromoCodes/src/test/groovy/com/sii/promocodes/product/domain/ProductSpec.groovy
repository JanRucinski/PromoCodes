package com.sii.promocodes.product.domain

import com.sii.promocodes.commons.db.RandomStringGenerator
import static com.sii.promocodes.product.domain.ProductFixture.*

class ProductSpec extends ProductBaseSpec{

        def "should create a product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(ID)

            when:
            var response = productFacade.createProduct(createProductRequest())

            then:
            response == createProductDto()
        }

        def "should get all products"() {
            given:
            RandomStringGenerator.setFixedUuidValue(ID)
            productFacade.createProduct(createProductRequest())
            RandomStringGenerator.setFixedUuidValue(ID_2)
            productFacade.createProduct(createProductRequest({
                name = NAME_2
            }))

            when:
            var response = productFacade.getAllProducts()

            then:
            response.size() == 2
            response.stream().anyMatch { it.id == ID }
            response.stream().anyMatch { it.id == ID_2 }
            response.stream().anyMatch { it.name == NAME }
            response.stream().anyMatch { it.name == NAME_2 }
        }

        def "should update product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(ID)
            productFacade.createProduct(createProductRequest())

            when:
            var response = productFacade.getProductById(ID)

            then:
            response == createProductDto()
        }

        def "should delete product"() {
            given:
            RandomStringGenerator.setFixedUuidValue(ID)
            productFacade.createProduct(createProductRequest())

            when:
            productFacade.deleteProduct(ID)

            then:
            productFacade.getAllProducts().size() == 0
        }
}
