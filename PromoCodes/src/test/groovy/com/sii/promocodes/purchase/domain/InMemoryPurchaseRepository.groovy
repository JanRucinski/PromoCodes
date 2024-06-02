package com.sii.promocodes.purchase.domain

import com.sii.promocodes.common.InMemoryRepository

class InMemoryPurchaseRepository extends InMemoryRepository<Purchase, UUID> implements PurchaseRepository{
}
