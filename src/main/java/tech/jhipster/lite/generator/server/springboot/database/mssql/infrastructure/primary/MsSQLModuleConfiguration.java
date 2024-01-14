package tech.jhipster.lite.generator.server.springboot.database.mssql.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.MSSQL;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.SPRING_BOOT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.mssql.application.MsSQLApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class MsSQLModuleConfiguration {

  @Bean
  JHipsterModuleResource msSQLModule(MsSQLApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(MSSQL)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add MsSQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(SPRING_BOOT).build())
      .tags("server", "database", "mssql")
      .factory(applicationService::build);
  }
}
