package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterFileToMove(JHipsterProjectFilePath source, JHipsterDestination destination) {
  public JHipsterFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
