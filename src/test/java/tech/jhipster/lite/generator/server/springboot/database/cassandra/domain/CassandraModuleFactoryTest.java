package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

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
class CassandraModuleFactoryTest {

  private static final String DC = "datacenter1";

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private CassandraModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("cassandra")).thenReturn(new DockerImageVersion("cassandra", "4.0.7"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile(), logbackFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-cassandra</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>cassandra</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFile("documentation/cassandra.md")
      .and()
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/cassandra.yml up -d
        ```
        """
      )
      .and()
      .hasFile("src/main/docker/cassandra.yml")
      .containing("cassandra:4.0.7")
      .containing("CASSANDRA_DC=" + DC)
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          cassandra:
            schema-action: none
            local-datacenter: datacenter1
            contact-points: 127.0.0.1
            port: 9042
        '#spring':
          cassandra:
            keyspace-name: yourKeyspace
        """
      )
      .and()
      .hasFiles("src/test/java/com/jhipster/test/CassandraKeyspaceIT.java")
      .hasFile("src/test/java/com/jhipster/test/TestCassandraManager.java")
      .containing("cassandra:4.0.7")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/wire/cassandra/infrastructure/secondary",
        "CassandraDatabaseConfiguration.java",
        "CassandraJSR310DateConverters.java"
      )
      .hasFiles("src/test/java/com/jhipster/test/wire/cassandra/infrastructure/secondary/CassandraJSR310DateConvertersTest.java")
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          cassandra:
            local-datacenter: ${TEST_CASSANDRA_DC}
            port: ${TEST_CASSANDRA_PORT}
            contact-points: ${TEST_CASSANDRA_CONTACT_POINT}
            keyspace-name: ${TEST_CASSANDRA_KEYSPACE}
        """
      )
      .and()
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.jhipster.test.TestCassandraManager")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"com.datastax\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"com.datastax\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />");
  }
}
