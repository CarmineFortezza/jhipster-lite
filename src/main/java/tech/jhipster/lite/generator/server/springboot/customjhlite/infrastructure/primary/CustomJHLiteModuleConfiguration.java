package tech.jhipster.lite.generator.server.springboot.customjhlite.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.CUSTOM_JHLITE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.customjhlite.application.CustomJHLiteApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class CustomJHLiteModuleConfiguration {

  @Bean
  JHipsterModuleResource customJHLiteModule(CustomJHLiteApplicationService customJHLite) {
    return JHipsterModuleResource
      .builder()
      .slug(CUSTOM_JHLITE)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("JHLite", "Create a custom JHLite instance to build custom modules")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(customJHLite::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition
      .builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addServerPort()
      .addConfigurationFormat()
      .build();
  }
}
