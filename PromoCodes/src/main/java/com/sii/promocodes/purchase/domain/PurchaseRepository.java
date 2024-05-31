package com.sii.promocodes.purchase.domain;

import com.sii.promocodes.commons.db.BaseRepository;
import java.util.UUID;

public interface PurchaseRepository extends BaseRepository<Purchase, UUID> {

  @Override
  default String getEntityName() {
    return "Purchase";
  }
}
