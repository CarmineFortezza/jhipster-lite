package tech.jhipster.lite.module.domain.buildproperties;

import tech.jhipster.lite.shared.error.domain.Assert;

public record PropertyValue(String value) {
  public PropertyValue {
    Assert.notBlank("value", value);
  }

  public String get() {
    return value();
  }
}
