package com.sii.promocodes.pricecalculator.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PriceCalculatorApi {

  @Value
  public static class CalculatePriceRequest {

    @Schema(description = "id of the product", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    UUID productId;

    @Schema(description = "promo code", example = "PROMO2024")
    @NotNull
    String promoCode;

    public static CalculatePriceRequest fromPurchase(PurchaseApi.CreatePurchaseRequest request) {
      return new CalculatePriceRequest(request.getProductId(), request.getPromoCode());
    }
  }

  @Value
  public static class CalculatedPrice {

    BigDecimal calculatedPrice;

    @JsonIgnore
    BigDecimal discountAmount;

    Currency currency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String warning;
  }

}
