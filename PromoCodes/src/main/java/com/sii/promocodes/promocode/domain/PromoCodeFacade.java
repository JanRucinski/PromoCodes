package com.sii.promocodes.promocode.domain;

import com.sii.promocodes.promocode.dto.PromoCodeAlreadyExistsException;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import com.sii.promocodes.promocode.dto.PromoCodeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class PromoCodeFacade {

    private static final String PROMO_CODE_NOT_FOUND = "Promo code: %s not found";
    private static final String PROMO_CODE_ALREADY_EXISTS = "Promo code: %s already exists";
    private static final String PROMO_CODE_NOT_ACTIVE = "Promo code: %s is not active";

    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeApi.PromoCode createPromoCode(PromoCodeApi.CreatePromoCodeRequest request) {
        if (promoCodeRepository.existsById(request.getCode())) {
            throw new PromoCodeAlreadyExistsException(PROMO_CODE_ALREADY_EXISTS.formatted(request.getCode()));
        }

        PromoCode promoCode = new PromoCode(request);
        promoCodeRepository.save(promoCode);
        return promoCode.toDto();
    }

    public PromoCodeApi.PromoCode getPromoCode(String code) {
        return promoCodeRepository.findById(code)
                .map(this::handleExpiredCode)
                .map(PromoCode::toDto)
                .orElseThrow(() -> new PromoCodeNotFoundException(PROMO_CODE_NOT_FOUND.formatted(code)));
    }

    private PromoCode handleExpiredCode(PromoCode promoCode) {
        if (promoCode.isExpired()) {
            promoCode.expireCode();
        }
        return promoCode;
    }

    public List<PromoCodeApi.PromoCode> getAllPromoCodes() {
        return promoCodeRepository.findAll().stream()
                .map(this::handleExpiredCode)
                .map(PromoCode::toDto)
                .toList();
    }

    public void usePromoCode(String code) {
        PromoCode promoCode = promoCodeRepository.findById(code)
                .orElseThrow(() -> new PromoCodeNotFoundException(PROMO_CODE_NOT_FOUND.formatted(code)));

        if (!promoCode.isActive()) {
            throw new PromoCodeNotFoundException(PROMO_CODE_NOT_ACTIVE.formatted(code));
        }

        promoCode.useCode();
        promoCodeRepository.save(promoCode);
    }

}
