package tech.jhipster.lite.module.domain.javabuild.command;

import java.util.Collection;

public class JavaDependencyManagement extends JavaDependencyCommandsCreator {

  public JavaDependencyManagement(JavaDependency dependency) {
    super(dependency);
  }

  @Override
  protected Collection<JavaBuildCommand> dependencyCommands(ProjectJavaDependencies projectDependencies) {
    return dependency()
      .dependencyCommands(DependenciesCommandsFactory.MANAGEMENT, projectDependencies.dependencyManagement(dependency().id()));
  }
}
