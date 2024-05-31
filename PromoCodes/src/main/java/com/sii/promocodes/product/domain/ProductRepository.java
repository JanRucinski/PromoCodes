package com.sii.promocodes.product.domain;

import com.sii.promocodes.commons.db.BaseRepository;
import java.util.UUID;


public interface ProductRepository extends BaseRepository<Product, UUID> {

  @Override
  default String getEntityName() {
    return "Product";
  }

}
