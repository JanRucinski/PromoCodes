package com.sii.promocodes.pricecalculator.domain;

import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PriceCalculatorConfiguration {

  private final PromoCodeFacade promoCodeFacade;
  private final ProductFacade productFacade;

  @Bean
  public PriceCalculatorFacade priceCalculatorFacade() {
    return new PriceCalculatorFacade(productFacade, promoCodeFacade);
  }

}
