package com.sii.promocodes.purchase.domain;

import com.sii.promocodes.commons.enums.Currency;
import com.sii.promocodes.purchase.dto.PurchaseApi;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseReportHandler {

  private final PurchaseRepository purchaseRepository;

  public PurchaseApi.PurchaseReport generateSalesReport() {
    List<Purchase> purchases = purchaseRepository.findAll();

    Map<Currency, BigDecimal[]> reportData = purchases.stream()
        .map(Purchase::toDto)
        .collect(Collectors.groupingBy(PurchaseApi.Purchase::getCurrency,
            Collectors.collectingAndThen(
                Collectors.reducing(
                    new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                    this::calculateTotals,
                    this::accumulateTotals
                ),
                this::scaleTotals
            )
        ));

    List<PurchaseApi.SalesReportEntry> reportEntries = reportData.entrySet().stream()
        .map(this::createReportEntry)
        .collect(Collectors.toList());

    return new PurchaseApi.PurchaseReport(reportEntries);
  }

  private PurchaseApi.SalesReportEntry createReportEntry(Map.Entry<Currency, BigDecimal[]> entry) {
    Currency currency = entry.getKey();
    BigDecimal[] data = entry.getValue();
    return new PurchaseApi.SalesReportEntry(
        currency,
        data[0],
        data[1],
        data[2].intValue()
    );
  }

  private BigDecimal[] calculateTotals(PurchaseApi.Purchase p) {
    return new BigDecimal[]{p.getOriginalPrice(), p.getDiscountAmount(), BigDecimal.ONE};
  }

  private BigDecimal[] accumulateTotals(BigDecimal[] p1, BigDecimal[] p2) {
    return new BigDecimal[]{
        p1[0].add(p2[0]),
        p1[1].add(p2[1]),
        p1[2].add(BigDecimal.ONE)
    };
  }

  private BigDecimal[] scaleTotals(BigDecimal[] result) {
    return new BigDecimal[]{
        result[0].setScale(2, RoundingMode.HALF_UP),
        result[1].setScale(2, RoundingMode.HALF_UP),
        result[2].setScale(0, RoundingMode.HALF_UP)
    };
  }

}