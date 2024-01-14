package tech.jhipster.lite.generator.server.springboot.customjhlite.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class CustomJHLiteModuleFactoryTest {

  private static final CustomJHLiteModuleFactory factory = new CustomJHLiteModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, pomFile(), mainAppFile())
      .hasFile("pom.xml")
        .containing("<artifactId>cucumber-junit-platform-engine</artifactId>")
        .containing("<artifactId>cucumber-java</artifactId>")
        .containing("<artifactId>cucumber-spring</artifactId>")
        .containing("<artifactId>junit-platform-suite</artifactId>")
        .containing(
          """
              <dependency>
                <groupId>tech.jhipster.lite</groupId>
                <artifactId>jhlite</artifactId>
                <version>${jhlite.version}</version>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>tech.jhipster.lite</groupId>
                <artifactId>jhlite</artifactId>
                <version>${jhlite.version}</version>
                <type>test-jar</type>
                <classifier>tests</classifier>
                <scope>test</scope>
              </dependency>
          """
        )
        .and()
      .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          jhlite-hidden-resources:
            tags: banner
            # Disable the modules and its dependencies by slugs
            slugs: custom-jhlite
          server:
            port: 9000
          spring:
            jackson:
              default-property-inclusion: non_null
          """
        )
        .and()
      .hasFile("src/test/resources/config/application-test.yml")
        .containing(
          """
          server:
            port: 0
          spring:
            main:
              allow-bean-definition-overriding: true
          """)
        .and()
      .hasFile("src/main/java/com/jhipster/test/MyappApp.java")
        .containing("import tech.jhipster.lite.JHLiteApp;")
        .containing("@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, MyappApp.class })")
        .and()
      .hasPrefixedFiles("documentation", "module-creation.md", "cucumber.md")
      .doNotHaveFiles(
        "src/main/java/tech/jhipster/test/security/infrastructure/primary/CorsFilterConfiguration.java",
        "src/main/java/tech/jhipster/test/security/infrastructure/primary/CorsProperties.java",
        "src/test/java/tech/jhipster/test/security/infrastructure/primary/CorsFilterConfigurationIT.java"
      )
      .hasFile("src/test/java/com/jhipster/test/cucumber/CucumberTest.java")
        .containing("key = GLUE_PROPERTY_NAME, value = \"com.jhipster.test, tech.jhipster.lite.module.infrastructure.primary\"")
        .and()
      .hasFile("src/test/java/com/jhipster/test/cucumber/CucumberConfiguration.java")
        .containing("import com.jhipster.test.MyappApp;")
        .and()
      .hasFiles("src/test/java/com/jhipster/test/cucumber/rest/CucumberRestTemplate.java")
      .hasFiles("src/test/features/.gitkeep");
    //@formatter:on
  }

  private ModuleFile mainAppFile() {
    return file("src/test/resources/projects/files/MainApp.java", "src/main/java/com/jhipster/test/MyappApp.java");
  }
}
