package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.module.domain.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.AddGradlePlugin;
import tech.jhipster.lite.module.domain.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.SetBuildProperty;
import tech.jhipster.lite.module.domain.SetVersion;

public interface JavaDependenciesCommandHandler {
  void handle(SetVersion command);

  void handle(AddDirectJavaDependency command);

  void handle(RemoveDirectJavaDependency command);

  void handle(RemoveJavaDependencyManagement command);

  void handle(AddJavaDependencyManagement command);

  void handle(AddDirectMavenPlugin command);

  void handle(AddMavenPluginManagement command);

  void handle(AddMavenBuildExtension command);

  void handle(SetBuildProperty command);

  void handle(AddJavaBuildProfile command);

  void handle(AddGradlePlugin command);
}
