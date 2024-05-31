package com.sii.promocodes.purchase.infrastructure;

import com.sii.promocodes.purchase.domain.PurchaseFacade;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PurchaseController {

  private final PurchaseFacade purchaseFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PurchaseApi.Purchase createPurchase(
      @RequestBody @Valid PurchaseApi.CreatePurchaseRequest request) {
    return purchaseFacade.createPurchase(request);
  }

  @GetMapping("/report")
  public PurchaseApi.PurchaseReport generateSalesReport() {
    return purchaseFacade.generateSalesReport();
  }

}
