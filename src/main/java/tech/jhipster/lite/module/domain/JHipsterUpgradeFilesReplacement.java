package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterUpgradeFilesReplacement(JHipsterFileMatcher files, ElementReplacer replacer, String replacement) {
  public JHipsterUpgradeFilesReplacement {
    Assert.notNull("files", files);
    Assert.notNull("replacer", replacer);
    Assert.notNull("replacement", replacement);
  }
}
