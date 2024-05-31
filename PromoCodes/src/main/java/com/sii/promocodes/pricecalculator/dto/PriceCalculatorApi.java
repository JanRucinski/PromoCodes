package com.sii.promocodes.pricecalculator.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PriceCalculatorApi {

  @Value
  public static class CalculatePriceRequest {

    UUID productId;
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
