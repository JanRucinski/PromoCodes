package com.sii.promocodes.purchase.domain;

import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade;
import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PurchaseConfiguration {

  private final PurchaseRepository purchaseRepository;
  private final PriceCalculatorFacade priceCalculatorFacade;
  private final ProductFacade productFacade;
  private final PromoCodeFacade promoCodeFacade;

  @Bean
  PurchaseReportHandler purchaseReportHandler() {
    return new PurchaseReportHandler(purchaseRepository);
  }

  @Bean
  PurchaseFacade purchaseFacade() {
    return new PurchaseFacade(purchaseRepository, priceCalculatorFacade, productFacade,
        promoCodeFacade, purchaseReportHandler());
  }

}
