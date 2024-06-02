package com.sii.promocodes.purchase.domain

import static com.sii.promocodes.purchase.domain.PurchaseFixture.*

class PurchaseSpec extends PurchaseBaseSpec{

    def "should create a purchase"() {
        when:
        var response = purchaseFacade.createPurchase(createPurchaseRequest())

        then:
        response == createPurchaseDto()
    }

}