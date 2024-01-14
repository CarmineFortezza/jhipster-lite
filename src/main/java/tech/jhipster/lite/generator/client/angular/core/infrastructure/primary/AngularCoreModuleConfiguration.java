package tech.jhipster.lite.generator.client.angular.core.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class AngularCoreModuleConfiguration {

  @Bean
  JHipsterModuleResource angularModule(AngularApplicationService angular) {
    return JHipsterModuleResource
      .builder()
      .slug(ANGULAR_CORE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Angular", "Add Angular + Angular CLI")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "angular")
      .factory(angular::buildInitModule);
  }
}
