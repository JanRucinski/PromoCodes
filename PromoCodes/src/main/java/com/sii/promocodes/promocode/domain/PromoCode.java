package com.sii.promocodes.promocode.domain;

import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.commons.enums.Usability;
import com.sii.promocodes.promocode.dto.PromoCodeApi;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.sii.promocodes.commons.enums.Usability.*;

@Entity
@Table(name = "promo_code")
@NoArgsConstructor
public class PromoCode {

    @Id
    @NotNull
    private String code;

    @NotNull
    private int maxUsages;

    @NotNull
    private int currentUsages;

    @NotNull
    private Usability usability;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @NotNull

    private LocalDate expirationDate;

    PromoCode(PromoCodeApi.CreatePromoCodeRequest request) {
        this.code = request.getCode();
        this.maxUsages = request.getMaxUsages();
        this.currentUsages = 0;
        this.usability = ACTIVE;
        this.amount = request.getAmount();
        this.currency = request.getCurrency();
        this.expirationDate = request.getExpirationDate();
    }

    PromoCodeApi.PromoCode toDto() {
        return new PromoCodeApi.PromoCode(
                code,
                maxUsages,
                currentUsages,
                usability,
                amount,
                currency,
                expirationDate
        );
    }

    public void update(PromoCodeApi.UpdatePromoCodeRequest request) {
        if (request.getMaxUsages() != 0) {
            this.maxUsages = request.getMaxUsages();
        }
        if (request.getAmount() != null) {
            this.amount = request.getAmount();
        }
        if (request.getCurrency() != null) {
            this.currency = request.getCurrency();
        }
        if (request.getExpirationDate() != null) {
            this.expirationDate = request.getExpirationDate();
        }
    }

    boolean isExpired(){
        return LocalDate.now().isAfter(expirationDate);
    }

    void expireCode(){
        this.usability = EXPIRED;
    }

    void useCode() {
        this.currentUsages++;
        if (currentUsages >= maxUsages) {
            this.usability = DEPLETED;
        }
    }

    boolean isActive() {
        return usability == ACTIVE;
    }

}
