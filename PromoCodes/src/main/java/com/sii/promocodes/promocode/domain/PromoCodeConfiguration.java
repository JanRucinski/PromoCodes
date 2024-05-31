package com.sii.promocodes.promocode.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PromoCodeConfiguration {

  private final PromoCodeRepository promoCodeRepository;

  @Bean
  PromoCodeFacade promoCodeFacade() {
    return new PromoCodeFacade(promoCodeRepository);
  }

}
