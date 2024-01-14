package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.replacement.MandatoryReplacementException;
import tech.jhipster.lite.module.domain.replacement.MandatoryReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public record MandatoryFileReplacer(JHipsterProjectFilePath file, MandatoryReplacer replacement) implements ContentReplacer {
  public MandatoryFileReplacer {
    Assert.notNull("file", file);
    Assert.notNull("replacement", replacement);
  }

  @Override
  public String apply(String content) {
    return replacement().apply(content);
  }

  @Override
  public void handleError(Throwable e) {
    throw new MandatoryReplacementException(e);
  }
}
