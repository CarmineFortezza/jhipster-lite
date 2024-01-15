package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.AddGradlePlugin;
import tech.jhipster.lite.module.domain.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.JavaBuildCommand;
import tech.jhipster.lite.module.domain.JavaBuildCommands;
import tech.jhipster.lite.module.domain.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.SetBuildProperty;
import tech.jhipster.lite.module.domain.SetVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle.GradleCommandHandler;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.MavenCommandHandler;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Service
public class FileSystemJavaBuildCommandsHandler {

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, JavaBuildCommands commands) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (commands.isEmpty()) {
      return;
    }

    JavaDependenciesCommandHandler handler = buildCommandHandler(indentation, projectFolder);

    commands.get().forEach(command -> handle(handler, command));
  }

  private static JavaDependenciesCommandHandler buildCommandHandler(Indentation indentation, JHipsterProjectFolder projectFolder) {
    Path pomPath = projectFolder.filePath("pom.xml");
    if (Files.exists(pomPath)) {
      return new MavenCommandHandler(indentation, pomPath);
    }
    if (Files.exists(projectFolder.filePath("build.gradle.kts"))) {
      return new GradleCommandHandler(indentation, projectFolder);
    }
    throw new MissingJavaBuildConfigurationException(projectFolder);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Jacoco thinks there is a missed branch")
  private void handle(JavaDependenciesCommandHandler handler, JavaBuildCommand command) {
    switch (command) {
      case SetVersion setVersion -> handler.handle(setVersion);
      case SetBuildProperty setBuildProperty -> handler.handle(setBuildProperty);
      case RemoveJavaDependencyManagement removeJavaDependencyManagement -> handler.handle(removeJavaDependencyManagement);
      case AddJavaDependencyManagement addJavaDependencyManagement -> handler.handle(addJavaDependencyManagement);
      case RemoveDirectJavaDependency removeDirectJavaDependency -> handler.handle(removeDirectJavaDependency);
      case AddDirectJavaDependency addDirectJavaDependency -> handler.handle(addDirectJavaDependency);
      case AddDirectMavenPlugin addDirectMavenPlugin -> handler.handle(addDirectMavenPlugin);
      case AddMavenPluginManagement addMavenPluginManagement -> handler.handle(addMavenPluginManagement);
      case AddMavenBuildExtension addMavenBuildExtension -> handler.handle(addMavenBuildExtension);
      case AddJavaBuildProfile addJavaBuildProfile -> handler.handle(addJavaBuildProfile);
      case AddGradlePlugin addGradlePlugin -> handler.handle(addGradlePlugin);
    }
  }
}
