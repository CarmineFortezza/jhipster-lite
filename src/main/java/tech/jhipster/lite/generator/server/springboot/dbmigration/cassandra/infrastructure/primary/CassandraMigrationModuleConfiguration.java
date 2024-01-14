package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.CASSANDRA;
import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.CASSANDRA_MIGRATION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.application.CassandraMigrationApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class CassandraMigrationModuleConfiguration {

  @Bean
  JHipsterModuleResource cassandraMigrationModule(CassandraMigrationApplicationService cassandraMigration) {
    return JHipsterModuleResource
      .builder()
      .slug(CASSANDRA_MIGRATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Cassandra Migration tools")
      .organization(JHipsterModuleOrganization.builder().addDependency(CASSANDRA).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "cassandra")
      .factory(cassandraMigration::buildModule);
  }
}
