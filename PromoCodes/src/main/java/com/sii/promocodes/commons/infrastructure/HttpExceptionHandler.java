package com.sii.promocodes.commons.infrastructure;

import com.sii.promocodes.promocode.dto.PromoCodeAlreadyExistsException;
import com.sii.promocodes.promocode.dto.PromoCodeNotFoundException;
import com.sii.promocodes.promocode.dto.PromoCodeNotUsableException;
import com.sii.promocodes.promocode.dto.PromoCodeValidationException;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler(PromoCodeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handlePromoCodeNotFoundException(PromoCodeNotFoundException e) {
    return new ErrorResponse(e);
  }

  @ExceptionHandler(PromoCodeAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handlePromoCodeAlreadyExistsException(PromoCodeAlreadyExistsException e) {
    return new ErrorResponse(e);
  }

  @ExceptionHandler(PromoCodeNotUsableException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  public ErrorResponse handlePromoCodeNotUsableException(PromoCodeNotUsableException e) {
    return new ErrorResponse(e);
  }

  @ExceptionHandler(PromoCodeValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePromoCodeValidationException(PromoCodeValidationException e) {
    return new ErrorResponse(e);
  }

  @AllArgsConstructor
  @Value
  public static class ErrorResponse {

    String message;

    public ErrorResponse(Exception e) {
      this.message = e.getMessage();
    }
  }

}
