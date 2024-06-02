package com.sii.promocodes.promocode.dto;

import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.commons.enums.DiscountType;
import com.sii.promocodes.commons.enums.Usability;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PromoCodeApi {

  @Value
  public static class PromoCode {

    @Schema(description = "promo code", example = "PROMO2024")
    @NotNull
    String code;

    @Schema(description = "max usages", example = "10")
    @Positive
    @NotNull
    int maxUsages;

    @Schema(description = "current amount of usages")
    @NotNull
    int currentUsages;

    @Schema(description = "the status of usability o the code", example = "ACTIVE")
    @NotNull
    Usability usability;

    @Schema(description = "the amount by which the promo code reduces the price", example = "10.0")
    @Positive
    @NotNull
    BigDecimal amount;

    @Schema(description = "the currency of the promo code", example = "USD")
    @NotNull
    Currency currency;

    @Schema(description = "the expiration date of the promo code", example = "2024-12-31")
    @NotNull
    LocalDate expirationDate;

    @Schema(description = "the type of discount", example = "FLAT")
    @NotNull
    DiscountType discountType;
  }

  @Value
  public static class CreatePromoCodeRequest {

    @NotNull
    String code;

    @Positive
    @NotNull
    int maxUsages;

    @Positive
    @NotNull
    BigDecimal amount;

    @NotNull
    Currency currency;

    @NotNull
    LocalDate expirationDate;

    @NotNull
    DiscountType discountType;
  }

}
