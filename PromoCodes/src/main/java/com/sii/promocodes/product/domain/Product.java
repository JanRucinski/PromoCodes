package com.sii.promocodes.product.domain;

import com.sii.promocodes.commons.db.RandomStringGenerator;
import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.product.dto.ProductApi;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
class Product {

  @Id
  @NotNull
  private UUID id;

  @NotBlank
  private String name;

  private String description;

  @NotNull
  private BigDecimal price;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Currency currency;

  Product(ProductApi.CreateProductRequest request) {
    this.id = RandomStringGenerator.randomUUID();
    this.name = request.getName();
    this.description = request.getDescription();
    this.price = request.getPrice();
    this.currency = request.getCurrency();
  }

  ProductApi.Product toDto() {
    return new ProductApi.Product(
        id,
        name,
        description,
        price,
        currency
    );
  }

  public void update(ProductApi.UpdateProductRequest request) {
    if (request.getName() != null) {
      this.name = request.getName();
    }
    if (request.getDescription() != null) {
      this.description = request.getDescription();
    }
    if (request.getPrice() != null) {
      this.price = request.getPrice();
    }
    if (request.getCurrency() != null) {
      this.currency = request.getCurrency();
    }
  }
}
