package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PostgresqlModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private PostgresqlModuleFactory factory;

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get("postgres")).thenReturn(new DockerImageVersion("postgres", "0.0.0"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("src/main/java/com/jhipster/test/wire/postgresql/infrastructure/secondary", "DatabaseConfiguration.java")
      .hasFile("documentation/postgresql.md")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.postgresql</groupId>
              <artifactId>postgresql</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.hibernate.orm</groupId>")
      .containing("<artifactId>hibernate-core</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          datasource:
            hikari:
              poolName: Hikari
              auto-commit: false
            password: ''
            driver-class-name: org.postgresql.Driver
            username: myapp
            url: jdbc:postgresql://localhost:5432/myapp
            type: com.zaxxer.hikari.HikariDataSource
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
          jpa:
            properties:
              hibernate:
                jdbc:
                  time_zone: UTC
                  batch_size: 25
                query:
                  fail_on_pagination_over_collection_fetch: true
                  in_clause_parameter_padding: true
                generate_statistics: false
                order_updates: true
                connection:
                  provider_disables_autocommit: true
                order_inserts: true
            hibernate:
              ddl-auto: none
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            username: myapp
            hikari:
              maximum-pool-size: 2
            url: jdbc:tc:postgresql:0.0.0:///myapp?TC_TMPFS=/testtmpfs:rw
            password: ''
        """
      );
  }
}
