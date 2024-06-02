package com.sii.promocodes.promocode.domain;

import com.sii.promocodes.commons.enums.DiscountType;
import com.sii.promocodes.promocode.dto.PromoCodeAlreadyExistsException;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import com.sii.promocodes.promocode.dto.PromoCodeNotFoundException;
import com.sii.promocodes.promocode.dto.PromoCodeNotUsableException;
import com.sii.promocodes.promocode.dto.PromoCodeValidationException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Transactional
@RequiredArgsConstructor
public class PromoCodeFacade {

  private static final String PROMO_CODE_NOT_FOUND = "Promo code: %s not found";
  private static final String PROMO_CODE_ALREADY_EXISTS = "Promo code: %s already exists";
  private static final String PROMO_CODE_NOT_ACTIVE = "Promo code: %s is not active";
  private static final String PROMO_CODE_TOO_SHORT = "Promo code: %s is too short";
  private static final String PROMO_CODE_TOO_LONG = "Promo code: %s is too long";
  private static final String PROMO_CODE_NOT_ALPHANUMERIC = "Promo code: %s is not made of alphanumeric characters";
  private static final String PROMO_CODE_CONTAINS_WHITESPACES = "Promo code: %s contains whitespaces";
  private static final String PROMO_CODE_AMOUNT_LESS_THAN_0 = "Promo code amount: %s is smaller than 0";
  private static final String PROMO_CODE_PERCENTAGE_MORE_THAN_100 = "Promo code percentage amount: %s is greater than 100";
  private static final int MAX_PROMO_CODE_LENGTH = 24;
  private static final int MIN_PROMO_CODE_LENGTH = 3;
  private static final BigDecimal MIN_PROMO_CODE_AMOUNT = BigDecimal.ZERO;
  private static final BigDecimal MAX_PROMO_CODE_PERCENTAGE = BigDecimal.valueOf(100);
  private static final String PROMO_CODE_SYNTAX_REGEX = "[a-zA-Z0-9]+";

  private final PromoCodeRepository promoCodeRepository;

  public PromoCodeApi.PromoCode createPromoCode(PromoCodeApi.CreatePromoCodeRequest request) {

    validatePromoCodeRequest(request);

    if (promoCodeRepository.existsById(request.getCode())) {
      throw new PromoCodeAlreadyExistsException(
          PROMO_CODE_ALREADY_EXISTS.formatted(request.getCode()));
    }

    PromoCode promoCode = new PromoCode(request);
    promoCodeRepository.save(promoCode);
    return promoCode.toDto();
  }

  private void validatePromoCodeRequest(PromoCodeApi.CreatePromoCodeRequest request) {
    validatePromoCodeLength(request.getCode());
    validatePromoCodeSyntax(request.getCode());
    validatePromoCodeAmount(request.getAmount(), request.getDiscountType());
  }

  private void validatePromoCodeLength(String code) {
    if (code.length() < MIN_PROMO_CODE_LENGTH) {
      throw new PromoCodeValidationException(PROMO_CODE_TOO_SHORT.formatted(code));
    }
    if (code.length() > MAX_PROMO_CODE_LENGTH) {
      throw new PromoCodeValidationException(PROMO_CODE_TOO_LONG.formatted(code));
    }
  }

  private void validatePromoCodeSyntax(String code) {
    if (StringUtils.containsWhitespace(code)) {
      throw new PromoCodeValidationException(PROMO_CODE_CONTAINS_WHITESPACES.formatted(code));
    }
    if (!code.matches(PROMO_CODE_SYNTAX_REGEX)) {
      throw new PromoCodeValidationException(PROMO_CODE_NOT_ALPHANUMERIC.formatted(code));
    }
  }

  private void validatePromoCodeAmount(BigDecimal amount, DiscountType discountType) {
    if (amount.compareTo(MIN_PROMO_CODE_AMOUNT) <= 0) {
      throw new PromoCodeValidationException(PROMO_CODE_AMOUNT_LESS_THAN_0.formatted(amount));
    }
    if (discountType == DiscountType.PERCENTAGE && amount.compareTo(MAX_PROMO_CODE_PERCENTAGE) > 0) {
      throw new PromoCodeValidationException(PROMO_CODE_PERCENTAGE_MORE_THAN_100.formatted(amount));
    }
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
      throw new PromoCodeNotUsableException(PROMO_CODE_NOT_ACTIVE.formatted(code));
    }

    promoCode.useCode();
    promoCodeRepository.save(promoCode);
  }

}
