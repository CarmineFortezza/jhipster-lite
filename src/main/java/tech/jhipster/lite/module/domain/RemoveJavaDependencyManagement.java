package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.shared.error.domain.Assert;

public record RemoveJavaDependencyManagement(DependencyId dependency) implements JavaBuildCommand {
  public RemoveJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
  }
}
