package tech.jhipster.lite.module.domain;

@FunctionalInterface
public interface JHipsterModuleFactory {
  JHipsterModule create(JHipsterModuleProperties properties);
}
