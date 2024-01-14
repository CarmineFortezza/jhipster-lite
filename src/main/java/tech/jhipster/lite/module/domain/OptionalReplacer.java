package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

record OptionalReplacer(ElementReplacer replacer, String updatedValue) {
  OptionalReplacer {
    Assert.notNull("replacer", replacer);
    Assert.notNull("updatedValue", updatedValue);
  }

  public String apply(String content) {
    if (replacer.dontNeedReplacement(content, updatedValue())) {
      return content;
    }

    return replacer().replacement().apply(content, updatedValue());
  }
}
