package tech.jhipster.lite.generator.server.springboot.cucumbercommon.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.javabuild.command.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;

public final class CucumbersModules {

  private static final String CUCUMBER_GROUP_ID = "io.cucumber";
  private static final String CUCUMBER_VERSION = "cucumber.version";

  private CucumbersModules() {}

  public static JHipsterModuleBuilder cucumberModuleBuilder(JHipsterModuleProperties properties) {
    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(cucumberJunitPlatformEngineDependency())
      .addDependency(cucumberJavaDependency())
      .addDependency(cucumberSpringDependency())
      .addDependency(junitPlatformSuiteDependency())
      .and();
  }

  private static JavaDependency cucumberJunitPlatformEngineDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-junit-platform-engine")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency cucumberJavaDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-java")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency cucumberSpringDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-spring")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency junitPlatformSuiteDependency() {
    return javaDependency().groupId("org.junit.platform").artifactId("junit-platform-suite").scope(JavaDependencyScope.TEST).build();
  }
}
