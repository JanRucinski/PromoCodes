package com.sii.promocodes.promocode.domain

import com.sii.promocodes.common.InMemoryRepository

class InMemoryPromoCodeRepository extends InMemoryRepository<PromoCode, String> implements PromoCodeRepository{

    @Override
    PromoCode save (PromoCode promoCode){
        super.save(promoCode.code, promoCode)
        return promoCode
    }
}
