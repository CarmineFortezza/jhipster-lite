package tech.jhipster.lite.generator.server.javatool.memoizer.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.memoizer.application.JavaMemoizersApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class JavaMemoizersModuleConfiguration {

  @Bean
  JHipsterModuleResource javaMemoizersModule(JavaMemoizersApplicationService javaMemoizers) {
    return JHipsterModuleResource
      .builder()
      .slug(JAVA_MEMOIZERS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple memoizers factory")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(javaMemoizers::buildModule);
  }
}
