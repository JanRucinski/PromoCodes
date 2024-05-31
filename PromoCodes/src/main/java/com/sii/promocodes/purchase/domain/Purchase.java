package com.sii.promocodes.purchase.domain;

import com.sii.promocodes.commons.db.RandomStringGenerator;
import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase")
@NoArgsConstructor
public class Purchase {

  @Id
  @NotNull
  private UUID id;

  @NotNull
  private UUID productId;

  @NotNull
  private String productName;

  @NotNull
  private BigDecimal originalPrice;

  private BigDecimal discountAmount;

  @NotNull
  private Currency currency;

  @NotNull
  private LocalDate purchaseDate;

  Purchase(PurchaseApi.Purchase purchase) {
    this.id = RandomStringGenerator.randomUUID();
    this.productId = purchase.getProductId();
    this.productName = purchase.getProductName();
    this.originalPrice = purchase.getOriginalPrice();
    this.discountAmount = purchase.getDiscountAmount();
    this.currency = purchase.getCurrency();
    this.purchaseDate = purchase.getPurchaseDate();
  }

  PurchaseApi.Purchase toDto() {
    return new PurchaseApi.Purchase(
        productId,
        productName,
        originalPrice,
        discountAmount,
        currency,
        purchaseDate
    );
  }

}
