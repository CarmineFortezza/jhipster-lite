package tech.jhipster.lite.generator.server.springboot.async.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModule;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class SpringBootAsyncModuleFactoryTest {

  private static final SpringBootAsyncModuleFactory factory = new SpringBootAsyncModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasJavaSources("com/jhipster/test/wire/async/infrastructure/secondary/AsyncConfiguration.java")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          task:
            scheduling:
              thread-name-prefix: myapp-scheduling-
              pool:
                size: 2
            execution:
              pool:
                keep-alive: 10s
                queue-capacity: 100
                max-size: 16
              thread-name-prefix: myapp-task-
        """
      );
  }
}
