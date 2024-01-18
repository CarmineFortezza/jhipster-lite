package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.shared.error.domain.Assert;

public record AddJavaDependencyManagement(JavaDependencyInterfaceCD dependency) implements JavaBuildCommand, AddJavaDependency {
  public AddJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
  }
}
