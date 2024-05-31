package com.sii.promocodes.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sii.promocodes.commons.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProductApi {

  @Value
  public static class Product {

    UUID id;

    @Schema(description = "name of the product", example = "Toaster")
    String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description;

    BigDecimal price;

    Currency currency;
  }

  @Value
  public static class CreateProductRequest {

    String name;
    String description;
    BigDecimal price;
    Currency currency;
  }

  @Value
  public static class UpdateProductRequest {

    String name;
    String description;
    BigDecimal price;
    Currency currency;
  }
}
