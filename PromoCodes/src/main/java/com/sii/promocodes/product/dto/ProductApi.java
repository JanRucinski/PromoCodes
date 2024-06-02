package com.sii.promocodes.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sii.promocodes.commons.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProductApi {

  @Value
  public static class Product {

    @Schema(description = "id of the product", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id;

    @Schema(description = "name of the product, must not be blank", example = "Toaster")
    @NotBlank
    String name;

    @Schema(description = "description of the product", example = "A toaster that toasts bread")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description;

    @Schema(description = "price of the product, must not be negative", example = "10.00")
    @NotNull
    @Min(0)
    BigDecimal price;

    @Schema(description = "currency of the product", example = "USD")
    @NotNull
    Currency currency;
  }

  @Value
  public static class CreateProductRequest {

    @NotBlank
    String name;

    String description;

    @NotNull
    @Min(0)
    BigDecimal price;

    @NotNull
    Currency currency;
  }

  @Value
  public static class UpdateProductRequest {

    @NotBlank
    String name;

    String description;

    @Min(0)
    BigDecimal price;

    Currency currency;
  }
}
