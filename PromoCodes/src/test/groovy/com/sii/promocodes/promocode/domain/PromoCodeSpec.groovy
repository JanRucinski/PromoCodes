package com.sii.promocodes.promocode.domain

import com.sii.promocodes.commons.enums.DiscountType

import static com.sii.promocodes.promocode.domain.PromoCodeFixture.*

class PromoCodeSpec extends PromoCodeBaseSpec{

    def "should create a promo code with type FLAT"() {
        when:
        var response = promoCodeFacade.createPromoCode(createPromoCodeRequest())

        then:
        response == createPromoCodeDto()
    }

    def "should create a promo code with type PERCENTAGE"() {
        when:
        var response = promoCodeFacade.createPromoCode(createPromoCodeRequest({
            discountType = DiscountType.PERCENTAGE
        }))

        then:
        response == createPromoCodeDto({
            discountType = DiscountType.PERCENTAGE
        })
    }

    def "should get a promo code" () {
        given:
        promoCodeFacade.createPromoCode(createPromoCodeRequest())

        when:
        var response = promoCodeFacade.getPromoCode(EUR_CODE_ACTIVE)

        then:
        response.code == EUR_CODE_ACTIVE
    }

    def "should get all promo codes"() {
        given:
        promoCodeFacade.createPromoCode(createPromoCodeRequest())
        promoCodeFacade.createPromoCode(createPromoCodeRequest({
            code = EUR_CODE_EXPIRED
        }))

        when:
        var response = promoCodeFacade.getAllPromoCodes()

        then:
        response.size() == 2
        response.stream().anyMatch { it.code == EUR_CODE_ACTIVE }
        response.stream().anyMatch { it.code == EUR_CODE_EXPIRED }
    }
}
