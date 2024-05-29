package com.sii.promocodes.promocode.dto;

public class PromoCodeAlreadyExistsException extends RuntimeException {
    public PromoCodeAlreadyExistsException(String message) {
        super(message);
    }
}
