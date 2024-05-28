package com.sii.promocodes.product.domain

import com.sii.promocodes.common.InMemoryRepository

class InMemoryProductRepository extends InMemoryRepository<Product, UUID> implements ProductRepository{

}
