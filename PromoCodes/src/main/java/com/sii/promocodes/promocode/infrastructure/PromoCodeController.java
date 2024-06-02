package com.sii.promocodes.promocode.infrastructure;

import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Promo Code", description = "Promo code related operations")
@RequestMapping("/api/promo-code")
public class PromoCodeController {

  private final PromoCodeFacade promoCodeFacade;

  @Operation(summary = "Create a promo code", description = "Create a promo code with a code, max usages, discount amount, discount type, currency and expiration date")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PromoCodeApi.PromoCode createPromoCode(
      @RequestBody @Valid PromoCodeApi.CreatePromoCodeRequest request) {
    return promoCodeFacade.createPromoCode(request);
  }

  @Operation(summary = "Retrieve all promo codes", description = "Retrieve all promo codes")
  @GetMapping("/all")
  public List<PromoCodeApi.PromoCode> getPromoCodes() {
    return promoCodeFacade.getAllPromoCodes();
  }

  @Operation(summary = "Retrieve a promo code by code", description = "Retrieve a promo code by it's code")
  @GetMapping("/{code}")
  public PromoCodeApi.PromoCode getPromoCode(@PathVariable String code) {
    return promoCodeFacade.getPromoCode(code);
  }

}
