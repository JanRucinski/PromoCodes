package com.sii.promocodes.promocode.domain;

import com.sii.promocodes.commons.db.BaseRepository;

public interface PromoCodeRepository extends BaseRepository<PromoCode, String> {

        @Override
        default String getEntityName() {
            return "PromoCode";
        }
}
