package com.sii.promocodes.pricecalculator.infrastructure;

import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade;
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Price Calculator", description = "Price calculator related operations")
@RequestMapping("/api/calculate-price")
public class PriceCalculatorController {

  private final PriceCalculatorFacade priceCalculatorFacade;

  @Operation(summary = "Calculate price", description = "Calculate price for a product with a promo code")
  @PostMapping
  public PriceCalculatorApi.CalculatedPrice calculatePrice(
      @RequestBody @Valid PriceCalculatorApi.CalculatePriceRequest request) {
    return priceCalculatorFacade.calculatePrice(request);
  }

}
