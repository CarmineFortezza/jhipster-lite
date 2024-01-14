package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class GatewayModuleFactoryTest {

  private static final GatewayModuleFactory factory = new GatewayModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), propertiesFile())
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
              <artifactId>spring-cloud-starter-gateway</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          cloud:
            gateway:
              discovery:
                locator:
                  enabled: true
                  predicates[0]:
                    name: Path
                    args[pattern]: '''/services/''+serviceId.toLowerCase()+''/**'''
                  filters[0]:
                    args[replacement]: '''/${remaining}'''
                    name: RewritePath
                    args[regexp]: '''/services/'' + serviceId.toLowerCase() + ''/(?<remaining>.*)'''
                  lower-case-service-id: true
          application:
            name: myApp
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          cloud:
            gateway:
              discovery:
                locator:
                  enabled: false
          application:
            name: myApp
        """
      )
      .and()
      .hasJavaSources(
        "com/jhipster/test/wire/gateway/infrastructure/primary/GatewayResource.java",
        "com/jhipster/test/wire/gateway/infrastructure/primary/vm/RouteVM.java"
      )
      .hasJavaTests("com/jhipster/test/wire/gateway/infrastructure/primary/GatewayResourceIT.java");
  }
}
