package com.sii.promocodes.purchase.dto;

import com.sii.promocodes.commons.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Schema(description = "id of the purchase", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID productId;

    @Schema(description = "name of the product, must not be blank", example = "Toaster")
    @NotBlank
    String productName;

    @Schema(description = "original price of the product", example = "10.00")
    @NotNull
    @Min(0)
    BigDecimal originalPrice;

    @Schema(description = "discount amount", example = "1.00")
    BigDecimal discountAmount;

    @Schema(description = "the currency of the product", example = "USD")
    @NotNull
    Currency currency;

    @Schema(description = "the date of the purchase", example = "2024-12-31")
    @NotNull
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

    @NotNull
    UUID productId;

    @NotNull
    String promoCode;
  }

}
