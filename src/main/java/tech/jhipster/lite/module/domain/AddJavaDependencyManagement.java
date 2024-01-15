package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.shared.error.domain.Assert;

public record AddJavaDependencyManagement(JavaDependency dependency) implements JavaBuildCommand, AddJavaDependency {
  public AddJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
  }
}
