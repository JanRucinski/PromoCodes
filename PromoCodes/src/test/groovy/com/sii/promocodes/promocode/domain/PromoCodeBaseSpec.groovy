package com.sii.promocodes.promocode.domain

import com.sii.promocodes.common.InMemoryRepository
import spock.lang.Specification

class PromoCodeBaseSpec extends Specification{

    InMemoryRepository promoCodeRepository = new InMemoryPromoCodeRepository()

    PromoCodeConfiguration promoCodeConfiguration
    PromoCodeFacade promoCodeFacade

    def setup() {
        setupPromoCodeConfiguration()
        promoCodeFacade = new PromoCodeFacade(promoCodeRepository)
    }

    PromoCodeConfiguration setupPromoCodeConfiguration() {
        promoCodeConfiguration = new PromoCodeConfiguration(promoCodeRepository)
    }

}
