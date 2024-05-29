package com.sii.promocodes.pricecalculator.infrastructure;

import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade;
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import com.sii.promocodes.product.dto.ProductApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculate-price")
public class PriceCalculatorController {

    private final PriceCalculatorFacade priceCalculatorFacade;

    @PostMapping
    public PriceCalculatorApi.CalculatedPrice calculatePrice(@RequestBody @Valid PriceCalculatorApi.CalculatePriceRequest request){
        return priceCalculatorFacade.calculatePrice(request);
    }

}
