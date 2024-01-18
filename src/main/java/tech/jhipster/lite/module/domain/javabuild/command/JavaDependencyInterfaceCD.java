package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javabuild.DependencySlug;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyClassifier;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;

import java.util.Collection;
import java.util.Optional;

public interface JavaDependencyInterfaceCD {

  Optional<DependencySlug> slug();

  Optional<VersionSlug> version();

  JavaDependencyScope scope();

  boolean optional();

  Optional<JavaDependencyClassifier> classifier();

  DependencyId id();

    Optional<JavaDependencyType> type();

  Collection<DependencyId> exclusions();
}
