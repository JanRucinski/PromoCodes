package com.sii.promocodes.promocode.dto;

public class PromoCodeNotUsableException extends RuntimeException {

  public PromoCodeNotUsableException(String message) {
    super(message);
  }
}
