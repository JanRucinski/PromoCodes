package com.sii.promocodes.promocode.infrastructure;

import com.sii.promocodes.promocode.domain.PromoCodeFacade;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
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
@RequestMapping("/api/promo-code")
public class PromoCodeController {

  private final PromoCodeFacade promoCodeFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PromoCodeApi.PromoCode createPromoCode(
      @RequestBody @Valid PromoCodeApi.CreatePromoCodeRequest request) {
    return promoCodeFacade.createPromoCode(request);
  }

  @GetMapping("/all")
  public List<PromoCodeApi.PromoCode> getPromoCodes() {
    return promoCodeFacade.getAllPromoCodes();
  }

  @GetMapping("/{code}")
  public PromoCodeApi.PromoCode getPromoCode(@PathVariable String code) {
    return promoCodeFacade.getPromoCode(code);
  }

}
