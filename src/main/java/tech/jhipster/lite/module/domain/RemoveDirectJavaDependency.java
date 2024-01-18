package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.shared.error.domain.Assert;

public record RemoveDirectJavaDependency(DependencyId dependency) implements JavaBuildCommand {
  public RemoveDirectJavaDependency {
    Assert.notNull("dependency", dependency);
  }
}
