package com.sii.promocodes.product.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductConfiguration {

  private final ProductRepository productRepository;

  @Bean
  ProductFacade productFacade() {
    return new ProductFacade(productRepository);
  }

}
