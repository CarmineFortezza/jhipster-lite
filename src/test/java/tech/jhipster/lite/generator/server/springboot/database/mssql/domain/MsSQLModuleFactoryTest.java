package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

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
class MsSQLModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private MsSQLModuleFactory factory;

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get("mcr.microsoft.com/mssql/server")).thenReturn(new DockerImageVersion("mcr.microsoft.com/mssql/server", "0.0.0"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestAnnotation())
      .hasFile("src/main/java/com/jhipster/test/wire/mssql/infrastructure/secondary/DatabaseConfiguration.java")
      .and()
      .hasFile("documentation/mssql.md")
      .and()
      .hasFile("src/test/java/com/jhipster/test/MsSQLTestContainerExtension.java")
      .and()
      .hasFile("src/test/resources/container-license-acceptance.txt")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.microsoft.sqlserver</groupId>
              <artifactId>mssql-jdbc</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.hibernate.orm</groupId>")
      .containing("<artifactId>hibernate-core</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .containing("<artifactId>mssqlserver</artifactId>")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          jpa:
            properties:
              hibernate:
                criteria:
                  literal_handling_mode: BIND
                dialect: org.hibernate.dialect.SQLServer2012Dialect
                jdbc:
                  fetch_size: 150
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
                format_sql: true
            hibernate:
              ddl-auto: update
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
          datasource:
            hikari:
              poolName: Hikari
              auto-commit: false
            password: yourStrong(!)Password
            driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
            username: SA
            url: jdbc:sqlserver://localhost:1433;database=myapp;trustServerCertificate=true
            type: com.zaxxer.hikari.HikariDataSource
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            username: SA
            hikari:
              maximum-pool-size: 2
            url: jdbc:tc:sqlserver:///;database=myapp;trustServerCertificate=true?TC_TMPFS=/testtmpfs:rw
            password:"""
      );
  }

  private ModuleFile integrationTestAnnotation() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/jhipster/test/IntegrationTest.java");
  }
}
