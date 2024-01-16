package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javabuild.DependencySlug;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyClassifier;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;

import java.util.Optional;

public interface JavaDependencyInterfaceCD {

  Optional<DependencySlug> slug();

  Optional<VersionSlug> version();

  JavaDependencyScope scope();

  boolean optional();

  Optional<JavaDependencyClassifier> classifier();
}
