package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class FrontendMavenModuleConfiguration {

  @Bean
  JHipsterModuleResource frontendMavenModule(FrontendMavenApplicationService frontendMaven) {
    return JHipsterModuleResource
      .builder()
      .slug(FRONTEND_MAVEN_PLUGIN)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Frontend Maven Plugin", "Add Frontend Maven Plugin")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).addDependency(CLIENT_CORE).build())
      .tags("server", "tools")
      .factory(frontendMaven::buildModule);
  }
}
