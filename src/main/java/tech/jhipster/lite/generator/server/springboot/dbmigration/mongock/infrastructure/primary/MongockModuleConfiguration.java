package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.MONGOCK;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.MONGODB;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class MongockModuleConfiguration {

  @Bean
  JHipsterModuleResource mongockModule(MongockApplicationService mongock) {
    return JHipsterModuleResource
      .builder()
      .slug(MONGOCK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addConfigurationFormat().build())
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(JHipsterModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
