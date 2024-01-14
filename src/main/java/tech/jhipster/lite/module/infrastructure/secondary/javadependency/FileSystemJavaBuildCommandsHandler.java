package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddGradlePlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetBuildProperty;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
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
