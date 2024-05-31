package com.sii.promocodes.purchase.domain;

import static com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi.CalculatePriceRequest.fromPurchase;

import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade;
import com.sii.promocodes.pricecalculator.dto.PriceCalculatorApi;
import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.product.dto.ProductApi;
import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class PurchaseFacade {

  private final PurchaseRepository purchaseRepository;
  private final PriceCalculatorFacade priceCalculatorFacade;
  private final ProductFacade productFacade;
  private final PromoCodeFacade promoCodeFacade;
  private final PurchaseReportHandler purchaseReportHandler;

  public PurchaseApi.Purchase createPurchase(PurchaseApi.CreatePurchaseRequest request) {

    var calculatedPrice = priceCalculatorFacade.calculatePrice(fromPurchase(request));
    var product = productFacade.getProduct(request.getProductId());

    PurchaseApi.Purchase purchaseDto = processPurchase(calculatedPrice, product);
    promoCodeFacade.usePromoCode(request.getPromoCode());

    return purchaseDto;
  }

  private PurchaseApi.Purchase processPurchase(PriceCalculatorApi.CalculatedPrice calculatedPrice,
      ProductApi.Product product) {

    PurchaseApi.Purchase purchaseDto = new PurchaseApi.Purchase(
        product.getId(),
        product.getName(),
        product.getPrice(),
        calculatedPrice.getDiscountAmount(),
        product.getCurrency(),
        LocalDate.now()
    );

    purchaseRepository.save(new Purchase(purchaseDto));
    return purchaseDto;
  }

  public PurchaseApi.PurchaseReport generateSalesReport() {
    return purchaseReportHandler.generateSalesReport();
  }

}
