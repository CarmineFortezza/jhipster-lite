package tech.jhipster.lite.module.domain;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JavaDependencies {

  public static final JavaDependencies EMPTY = new JavaDependencies(null);

  private final Map<DependencyId, JavaDependencyInterfaceCD> dependencies;

  public JavaDependencies(Collection<JavaDependencyInterfaceCD> dependencies) {
    this.dependencies = buildDependencies(dependencies);
  }

  private Map<DependencyId, JavaDependencyInterfaceCD> buildDependencies(Collection<JavaDependencyInterfaceCD> dependencies) {
    if (dependencies == null) {
      return Map.of();
    }

    return dependencies.stream().collect(Collectors.toUnmodifiableMap(JavaDependencyInterfaceCD::id, Function.identity()));
  }

  public Optional<JavaDependencyInterfaceCD> get(DependencyId id) {
    Assert.notNull("id", id);

    return Optional.ofNullable(dependencies.get(id));
  }
}
