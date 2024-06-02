package com.sii.promocodes.commons.db;

import java.util.UUID;

public class RandomStringGenerator {

  private static UUID fixedUuidValue;

  private RandomStringGenerator() {
  }

  public static void setFixedUuidValue(UUID value) {
    fixedUuidValue = value;
  }

  public static UUID randomUUID() {
    if (fixedUuidValue == null) {
      return UUID.randomUUID();
    }
    return fixedUuidValue;
  }

}
