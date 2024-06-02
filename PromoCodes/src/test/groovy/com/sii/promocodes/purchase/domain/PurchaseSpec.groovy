package com.sii.promocodes.purchase.domain

import static com.sii.promocodes.purchase.domain.PurchaseFixture.*

class PurchaseSpec extends PurchaseBaseSpec{

    def "should create a purchase" () {
        when:
        var response = purchaseFacade.createPurchase(createPurchaseRequest())

        then:
        response == createPurchase()
    }

    def "should generate a report" () {
        when:
        var response = purchaseFacade.generateSalesReport()

        then:
        print(response)
    }

}