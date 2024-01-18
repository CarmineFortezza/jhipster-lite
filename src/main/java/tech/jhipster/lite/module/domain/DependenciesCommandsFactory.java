package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javadependency.DependencyId;

import java.util.function.Function;

public class DependenciesCommandsFactory {

  public static final DependenciesCommandsFactory MANAGEMENT = new DependenciesCommandsFactory(
    AddJavaDependencyManagement::new,
    RemoveJavaDependencyManagement::new
  );
  public static final DependenciesCommandsFactory DIRECT = new DependenciesCommandsFactory(
    AddDirectJavaDependency::new,
    RemoveDirectJavaDependency::new
  );

  private final Function<JavaDependencyInterfaceCD, JavaBuildCommand> addDependency;
  private final Function<DependencyId, JavaBuildCommand> removeDependency;

  private DependenciesCommandsFactory(
    Function<JavaDependencyInterfaceCD, JavaBuildCommand> addDependency,
    Function<DependencyId, JavaBuildCommand> removeDependency
  ) {
    this.addDependency = addDependency;
    this.removeDependency = removeDependency;
  }

  public JavaBuildCommand addDependency(JavaDependencyInterfaceCD javaDependency) {
    return addDependency.apply(javaDependency);
  }

  public JavaBuildCommand removeDependency(DependencyId id) {
    return removeDependency.apply(id);
  }
}
