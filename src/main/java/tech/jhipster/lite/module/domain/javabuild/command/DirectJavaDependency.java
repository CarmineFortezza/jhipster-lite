package tech.jhipster.lite.module.domain.javabuild.command;

import java.util.Collection;

public class DirectJavaDependency extends JavaDependencyCommandsCreator {

  public DirectJavaDependency(JavaDependency dependency) {
    super(dependency);
  }

  @Override
  protected Collection<JavaBuildCommand> dependencyCommands(ProjectJavaDependencies projectDependencies) {
    return dependency().dependencyCommands(DependenciesCommandsFactory.DIRECT, projectDependencies.dependency(dependency().id()));
  }
}
