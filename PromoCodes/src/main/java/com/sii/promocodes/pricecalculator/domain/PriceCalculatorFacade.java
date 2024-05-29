package com.sii.promocodes.pricecalculator.domain;

import com.sii.promocodes.commons.enums.Usability;
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.product.dto.ProductApi;
import com.sii.promocodes.promocode.domain.PromoCode;
import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Transactional
@RequiredArgsConstructor
public class PriceCalculatorFacade {

    private static final String CODE_EXPIRED_MESSAGE = "The PromoCode is Expired, the price was not adjusted";
    private static final String CODE_DEPLETED_MESSAGE = "The PromoCode is Depleted, the price was not adjusted";
    private static final String CURRENCY_NOT_MATCH_MESSAGE = "The currencies do not match, the price was not adjusted";

    private final ProductFacade productFacade;
    private final PromoCodeFacade promoCodeFacade;

    public PriceCalculatorApi.CalculatedPrice calculatePrice(PriceCalculatorApi.CalculatePriceRequest request) {
        var promoCode = promoCodeFacade.getPromoCode(request.getPromoCode());
        var product = productFacade.getProduct(request.getProductId());

        PriceCalculatorApi.CalculatedPrice validationResponse = validatePromoCodeAndCurrency(promoCode, product);
        if (validationResponse != null) {
            return validationResponse;
        }

        BigDecimal calculatedPrice = calculateDiscountedPrice(product.getPrice(), promoCode.getAmount());

        return new PriceCalculatorApi.CalculatedPrice(calculatedPrice, product.getCurrency(), null);
    }

    private PriceCalculatorApi.CalculatedPrice validatePromoCodeAndCurrency(PromoCodeApi.PromoCode promoCode, ProductApi.Product product) {

        String usabilityMessage = getUsabilityMessage(promoCode);

        if (usabilityMessage != null) {
            return new PriceCalculatorApi.CalculatedPrice(product.getPrice(), product.getCurrency(), usabilityMessage);
        }

        if (!isCurrencyMatching(promoCode, product)) {
            return new PriceCalculatorApi.CalculatedPrice(product.getPrice(), product.getCurrency(), CURRENCY_NOT_MATCH_MESSAGE);
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

    private BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountAmount) {
        BigDecimal discountedPrice = originalPrice.subtract(discountAmount);
        return discountedPrice.max(BigDecimal.ZERO);
    }

}
