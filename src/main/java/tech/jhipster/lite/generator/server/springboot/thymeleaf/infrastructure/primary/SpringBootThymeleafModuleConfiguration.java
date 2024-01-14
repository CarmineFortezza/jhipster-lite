package tech.jhipster.lite.generator.server.springboot.thymeleaf.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.SPRING_BOOT_THYMELEAF;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.thymeleaf.application.SpringBootThymeleafApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class SpringBootThymeleafModuleConfiguration {

  @Bean
  public JHipsterModuleResource springBootThymeleafModule(SpringBootThymeleafApplicationService thymeleafModule) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_THYMELEAF)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Spring Boot Thymeleaf to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(thymeleafModule::buildSpringBootThymeleafModule);
  }
}
