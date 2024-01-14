package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

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
class SpringCloudConfigModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SpringCloudConfigModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("jhipster/jhipster-registry")).thenReturn(new DockerImageVersion("jhipster/jhipster-registry", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing(
        """
              <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-bootstrap</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          cloud:
            config:
              retry:
                initial-interval: 1000
                max-attempts: 100
                max-interval: 2000
              name: myApp
              label: main
              uri: http://admin:${jhipster.registry.password}@localhost:8761/config
              fail-fast: true
            compatibility-verifier:
              enabled: false
          application:
            name: myApp
        jhipster:
          registry:
            password: admin
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap-local.yml")
      .containing(
        """
        spring:
          cloud:
            config:
              retry:
                initial-interval: 1000
                max-attempts: 100
                max-interval: 2000
              name: myApp
              label: main
              uri: http://admin:${jhipster.registry.password}@localhost:8761/config
              fail-fast: false
            compatibility-verifier:
              enabled: false
          application:
            name: myApp
        jhipster:
          registry:
            password: admin
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          application:
            name: myApp
          cloud:
            config:
              enabled: false
        """
      )
      .and()
      .hasFiles("src/main/docker/jhipster-registry.yml", "src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
