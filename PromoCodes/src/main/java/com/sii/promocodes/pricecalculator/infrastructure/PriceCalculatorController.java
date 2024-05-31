package com.sii.promocodes.pricecalculator.infrastructure;

import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade;
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculate-price")
public class PriceCalculatorController {

  private final PriceCalculatorFacade priceCalculatorFacade;

  @PostMapping
  public PriceCalculatorApi.CalculatedPrice calculatePrice(
      @RequestBody @Valid PriceCalculatorApi.CalculatePriceRequest request) {
    return priceCalculatorFacade.calculatePrice(request);
  }

}
