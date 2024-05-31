package com.sii.promocodes.pricecalculator.domain;

import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.product.dto.ProductApi;
import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class PriceCalculatorFacade {

  private static final String CODE_EXPIRED_MESSAGE = "The PromoCode is Expired, the price was not adjusted";
  private static final String CODE_DEPLETED_MESSAGE = "The PromoCode is Depleted, the price was not adjusted";
  private static final String CURRENCY_NOT_MATCH_MESSAGE = "The currencies do not match, the price was not adjusted";

  private static final BigDecimal DIVIDER = BigDecimal.valueOf(100);

  private final ProductFacade productFacade;
  private final PromoCodeFacade promoCodeFacade;

  public PriceCalculatorApi.CalculatedPrice calculatePrice(
      PriceCalculatorApi.CalculatePriceRequest request) {
    var promoCode = promoCodeFacade.getPromoCode(request.getPromoCode());
    var product = productFacade.getProduct(request.getProductId());

    PriceCalculatorApi.CalculatedPrice validationResponse = validatePromoCodeAndCurrency(promoCode,
        product);
    if (validationResponse != null) {
      return validationResponse;
    }

    BigDecimal calculatedPrice = calculateDiscountedPriceBasedOnType(promoCode, product);
    BigDecimal discountAmount = product.getPrice().subtract(calculatedPrice);

    return new PriceCalculatorApi.CalculatedPrice(
        calculatedPrice,
        discountAmount,
        product.getCurrency(),
        null
    );
  }

  private BigDecimal calculateDiscountedPriceBasedOnType(PromoCodeApi.PromoCode promoCode,
      ProductApi.Product product) {
    return switch (promoCode.getDiscountType()) {
      case FLAT -> calculateFlatDiscountPrice(product.getPrice(), promoCode.getAmount());
      case PERCENTAGE ->
          calculatePercentageDiscountPrice(product.getPrice(), promoCode.getAmount());
    };
  }

  private PriceCalculatorApi.CalculatedPrice validatePromoCodeAndCurrency(
      PromoCodeApi.PromoCode promoCode, ProductApi.Product product) {

    String usabilityMessage = getUsabilityMessage(promoCode);

    if (usabilityMessage != null) {
      return new PriceCalculatorApi.CalculatedPrice(
          product.getPrice(),
          BigDecimal.ZERO,
          product.getCurrency(),
          usabilityMessage
      );
    }

    if (!isCurrencyMatching(promoCode, product)) {
      return new PriceCalculatorApi.CalculatedPrice(
          product.getPrice(),
          BigDecimal.ZERO,
          product.getCurrency(),
          CURRENCY_NOT_MATCH_MESSAGE
      );
    }

    return null;
  }

  private String getUsabilityMessage(PromoCodeApi.PromoCode promoCode) {
    return switch (promoCode.getUsability()) {
      case EXPIRED -> CODE_EXPIRED_MESSAGE;
      case DEPLETED -> CODE_DEPLETED_MESSAGE;
      default -> null;
    };
  }

  private boolean isCurrencyMatching(PromoCodeApi.PromoCode promoCode, ProductApi.Product product) {
    return promoCode.getCurrency().equals(product.getCurrency());
  }

  private BigDecimal calculateFlatDiscountPrice(BigDecimal originalPrice,
      BigDecimal discountAmount) {
    BigDecimal discountedPrice = originalPrice.subtract(discountAmount);
    return discountedPrice.max(BigDecimal.ZERO);
  }

  private BigDecimal calculatePercentageDiscountPrice(BigDecimal originalPrice,
      BigDecimal discountAmount) {
    BigDecimal discount = originalPrice.multiply(
        discountAmount.divide(DIVIDER, RoundingMode.HALF_UP));
    BigDecimal discountedPrice = originalPrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);

    return discountedPrice.max(BigDecimal.ZERO);
  }

}
