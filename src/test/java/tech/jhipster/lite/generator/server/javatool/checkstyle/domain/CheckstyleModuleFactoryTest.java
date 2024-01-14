package tech.jhipster.lite.generator.server.javatool.checkstyle.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class CheckstyleModuleFactoryTest {

  private final CheckstyleModuleFactory factory = new CheckstyleModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<maven-checkstyle-plugin.version>")
      .containing(
        """
              <plugin>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>${maven-checkstyle-plugin.version}</version>
                <executions>
                  <execution>
                    <id>validate</id>
                    <phase>validate</phase>
                    <goals>
                      <goal>check</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <configLocation>checkstyle.xml</configLocation>
                  <includeTestSourceDirectory>true</includeTestSourceDirectory>
                  <consoleOutput>true</consoleOutput>
                  <failsOnError>true</failsOnError>
                </configuration>
              </plugin>
        """
      )
      .and()
      .hasFile("checkstyle.xml")
      .containing("<module name=\"IllegalImport\">")
      .containing("<module name=\"Checker\">")
      .containing("<module name=\"TreeWalker\">")
      .containing("<module name=\"UnusedImports\" />");
  }

  @Test
  void shouldBuildModuleForGradle() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("build.gradle.kts")
      .containing(
        """
          checkstyle
          // jhipster-needle-gradle-plugins
        }
        """
      )
      .containing(
        """
        checkstyle {
          configFile = rootProject.file("checkstyle.xml")
          toolVersion = libs.versions.checkstyle.get()
        }
        """
      )
      .containing(
        """
        // Workaround for https://github.com/gradle/gradle/issues/27035
        configurations.checkstyle {
          resolutionStrategy.capabilitiesResolution.withCapability("com.google.collections:google-collections") {
            select("com.google.guava:guava:0")
          }
        }
        """
      )
      .and()
      .hasFile("gradle/libs.versions.toml")
      .containing("checkstyle = \"")
      .notContaining("maven-checkstyle-plugin");
  }
}
