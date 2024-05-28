package com.sii.promocodes.product.domain

import com.sii.promocodes.common.InMemoryRepository
import spock.lang.Specification

class ProductBaseSpec extends Specification {

    InMemoryRepository productRepository = new InMemoryProductRepository()

    ProductConfiguration productConfiguration
    ProductFacade productFacade

    def setup() {
        setupProductConfiguration()
        productFacade = new ProductFacade(productRepository)
    }

    ProductConfiguration setupProductConfiguration() {
        productConfiguration = new ProductConfiguration(productRepository)
    }


}
