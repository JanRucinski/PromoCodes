package com.sii.promocodes.purchase.dto;

import com.sii.promocodes.commons.enums.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PurchaseApi {

  @Value
  public static class Purchase {

    UUID productId;
    String productName;
    BigDecimal originalPrice;
    BigDecimal discountAmount;
    Currency currency;
    LocalDate purchaseDate;
  }

  @Value
  public static class PurchaseReport {

    List<SalesReportEntry> sales;
  }

  @Value
  public static class SalesReportEntry {

    Currency currency;
    BigDecimal totalAmount;
    BigDecimal totalDiscount;
    Integer numberOfPurchases;
  }

  @Value
  public static class CreatePurchaseRequest {

    UUID productId;
    String promoCode;
  }

}
