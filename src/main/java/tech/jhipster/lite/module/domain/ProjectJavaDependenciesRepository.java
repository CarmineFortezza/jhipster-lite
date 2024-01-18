package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public interface ProjectJavaDependenciesRepository {
  ProjectJavaDependencies get(JHipsterProjectFolder folder);
}
