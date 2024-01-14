package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.mavenPlugin;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.buildproperties.BuildProperty;
import tech.jhipster.lite.module.domain.buildproperties.PropertyKey;
import tech.jhipster.lite.module.domain.buildproperties.PropertyValue;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddGradlePlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetBuildProperty;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileActivation;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class MavenCommandHandlerTest {

  @Test
  void shouldNotCreateHandlerFromRandomFile() {
    assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, Paths.get("src/test/resources/projects/empty/.gitkeep")))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAppendEncodingHeader() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

    assertThat(content(pom)).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
  }

  @Test
  void shouldEnforceIndentation() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.from(4), pom).handle(new SetVersion(springBootVersion()));

    assertThat(content(pom))
      .contains("    <properties>")
      .contains("        <spring-boot.version>1.2.3</spring-boot.version>")
      .contains("    </properties>");
  }

  @Nested
  @DisplayName("Set dependency version")
  class MavenCommandHandlerSetVersionTest {

    @Test
    void shouldAddPropertiesToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(content(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithoutProperties() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(content(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithProperties() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(content(pom)).contains("    <spring-boot.version>1.2.3</spring-boot.version>").doesNotContain(">  ");
    }

    @Test
    void shouldUpdateExistingProperty() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(new JavaDependencyVersion("json-web-token", "0.12.0")));

      assertThat(content(pom))
        .contains("    <json-web-token.version>0.12.0</json-web-token.version>")
        .doesNotContain("    <json-web-token.version>0.11.5</json-web-token.version>")
        .doesNotContain(">  ");
    }
  }

  @Nested
  @DisplayName("Set build property")
  class HandleSetBuildProperty {

    @Nested
    class WithoutProfile {

      @Test
      void shouldAddPropertiesToPomWithOnlyRootDefined() {
        Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(content(pom))
          .contains(
            """
              <properties>
                <spring.profiles.active>local</spring.profiles.active>
              </properties>
            """
          );
      }

      @Test
      void shouldAddPropertiesToPomWithoutProperties() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(content(pom))
          .contains(
            """
              <properties>
                <spring.profiles.active>local</spring.profiles.active>
              </properties>
            """
          );
      }

      @Test
      void shouldAddPropertiesToPomWithProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(content(pom)).contains("    <spring.profiles.active>local</spring.profiles.active>").doesNotContain(">  ");
      }

      @Test
      void shouldUpdateExistingProperty() {
        Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom)
          .handle(new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev"))));

        assertThat(content(pom))
          .contains("    <spring.profiles.active>dev</spring.profiles.active>")
          .doesNotContain("    <spring.profiles.active>local</spring.profiles.active>")
          .doesNotContain(">  ");
      }
    }

    @Nested
    class WithProfile {

      @Test
      void shouldNotAddPropertiesToPomWithoutProfile() {
        Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

        assertThatThrownBy(() ->
            new MavenCommandHandler(Indentation.DEFAULT, pom)
              .handle(new SetBuildProperty(springProfilesActiveProperty(), localMavenProfile()))
          )
          .isExactlyInstanceOf(MissingMavenProfileException.class);
      }

      @Test
      void shouldAddPropertiesToPomProfileWithoutProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty(), localMavenProfile()));

        assertThat(content(pom))
          .contains(
            """
              <profiles>
                <profile>
                  <id>local</id>
                  <properties>
                    <spring.profiles.active>local</spring.profiles.active>
                  </properties>
                </profile>
              </profiles>
            """
          );
      }

      @Test
      void shouldAddPropertiesToPomProfileWithProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile-and-properties/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty(), localMavenProfile()));

        assertThat(content(pom))
          .contains(
            """
                    <spring.profiles.active>local</spring.profiles.active>
                  </properties>
                </profile>
              </profiles>
            """
          )
          .doesNotContain(">  ");
      }

      @Test
      void shouldUpdateExistingProperty() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile-and-properties/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty(), localMavenProfile()));

        mavenCommandHandler.handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev")), localMavenProfile())
        );

        assertThat(content(pom))
          .contains(
            """
                    <spring.profiles.active>dev</spring.profiles.active>
                  </properties>
                </profile>
              </profiles>
            """
          )
          .doesNotContain("<spring.profiles.active>local</spring.profiles.active>");
      }
    }
  }

  @Nested
  @DisplayName("Add build profile")
  class HandleAddJavaBuildProfile {

    @Test
    void shouldAddProfileToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(localMavenProfile()));

      assertThat(content(pom))
        .contains(
          """
              <profile>
                <id>local</id>
              </profile>
            </profiles>
          """
        );
    }

    @Test
    void shouldAddProfileToPomWithoutProfiles() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom)
        .handle(new AddJavaBuildProfile(localMavenProfile(), BuildProfileActivation.builder().activeByDefault().build()));

      assertThat(content(pom))
        .contains(
          """
            <profiles>
              <profile>
                <id>local</id>
                <activation>
                  <activeByDefault>true</activeByDefault>
                </activation>
              </profile>
            </profiles>
          """
        );
    }

    @Test
    void shouldAddProfileToPomWithProfiles() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(new BuildProfileId("dev")));

      assertThat(content(pom))
        .contains(
          """
              <profile>
                <id>dev</id>
              </profile>
            </profiles>
          """
        );
    }

    @Test
    void shouldNotDuplicateExistingProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(localMavenProfile()));

      assertThat(content(pom))
        .contains(
          """
            <profiles>
              <profile>
                <id>local</id>
              </profile>
            </profiles>
          """
        );
    }

    @Test
    void shouldAddProfileWithEmptyActivation() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom)
        .handle(new AddJavaBuildProfile(localMavenProfile(), BuildProfileActivation.builder().build()));

      assertThat(content(pom))
        .contains(
          """
            <profiles>
              <profile>
                <id>local</id>
                <activation />
              </profile>
            </profiles>
          """
        );
    }
  }

  @Nested
  @DisplayName("Remove dependency management")
  class MavenCommandHandlerRemoveDependencyManagementTest {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependencyManagement(jsonWebTokenDependencyId()))
        )
        .doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyId()));

      assertThat(content(pom))
        .doesNotContain(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                  <type>pom</type>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }
  }

  @Nested
  @DisplayName("Add dependency management")
  class MavenCommandHandlerAddDependencyManagementTest {

    @Test
    void shouldAddDependencyInPomWithoutDependenciesManagement() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(content(pom))
        .contains(
          """
            <dependencyManagement>
              <dependencies>
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <type>pom</type>
                  <scope>import</scope>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }

    @Test
    void shouldAddDependencyInPomWithDependenciesManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(content(pom))
        .contains(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <type>pom</type>
                  <scope>import</scope>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }

    @Test
    void shouldAddDependencyManagementWithExclusion() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootStarterWebDependency()));

      assertThat(content(pom))
        .contains(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-web</artifactId>
                  <exclusions>
                    <exclusion>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-starter-tomcat</artifactId>
                    </exclusion>
                  </exclusions>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }
  }

  @Nested
  @DisplayName("Remove dependency")
  class MavenCommandHandlerRemoveDependencyTest {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()))
        )
        .doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .doesNotContain("      <groupId>io.jsonwebtoken</groupId>")
        .doesNotContain("      <artifactId>jjwt-api</artifactId>")
        .doesNotContain("      <version>${json-web-token.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }

    @Test
    void shouldRemoveDependencyWithFullyMatchingId() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-webtoken/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .contains(
          """
              <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-implementation</artifactId>
              </dependency>
              <dependency>
                <groupId>io.another</groupId>
                <artifactId>jjwt-api</artifactId>
              </dependency>
          """
        )
        .doesNotContain("      <version>${json-web-token.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }
  }

  @Nested
  @DisplayName("Add dependency")
  class MavenCommandHandlerAddDependencyTest {

    @Test
    void shouldAddDependencyInPomWithoutDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(optionalTestDependency()));

      assertThat(content(pom))
        .contains(
          """
            <dependencies>
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${spring-boot.version}</version>
                <classifier>test</classifier>
                <scope>test</scope>
                <optional>true</optional>
              </dependency>
            </dependencies>
          """
        );
    }

    @Test
    void shouldAddTestDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(optionalTestDependency()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${spring-boot.version}</version>
                <classifier>test</classifier>
                <scope>test</scope>
                <optional>true</optional>
              </dependency>
            </dependencies>
          """
        );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }

    @Test
    void shouldAddDependencyWithExclusion() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(springBootStarterWebDependency()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <exclusions>
                  <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                  </exclusion>
                </exclusions>
              </dependency>
          """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <artifactId>logstash-logback-encoder</artifactId>
              </dependency>
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
              <dependency>
                <groupId>io.jsonwebtoken</groupId>
          """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithOnlyCompileDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-compile-only/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
            </dependencies>
          """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithEmptyDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
            </dependencies>
          """
        );
    }
  }

  @Nested
  @DisplayName("Add build plugin management")
  class MavenCommandHandlerAddMavenPluginManagementTest {

    @Test
    void shouldHandleMalformedConfiguration() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      AddMavenPluginManagement command = AddMavenPluginManagement
        .builder()
        .plugin(mavenPlugin().groupId("org.apache.maven.plugins").artifactId("maven-enforcer-plugin").configuration("<dummy").build())
        .build();
      assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command))
        .isExactlyInstanceOf(MalformedAdditionalInformationException.class);
    }

    @Test
    void shouldAddBuildPluginManagementToEmptyPom() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(pluginManagement());
    }

    @Test
    void shouldAddPropertyForPluginVersion() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom)
        .handle(AddMavenPluginManagement.builder().plugin(mavenEnforcerPlugin()).pluginVersion(mavenEnforcerVersion()));

      assertThat(content(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithoutPluginManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(pluginManagement())
        .doesNotContain(
          """
            <build>
            </build>
          """
        );
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithEmptyPluginManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-plugin-management/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(pluginManagement())
        .doesNotContain(
          """
            <build>
              <pluginManagement>
              </pluginManagement>
            </build>
          """
        );
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithPluginManagementPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(pluginManagement());
    }

    private void addMavenEnforcerPlugin(Path pom) {
      AddMavenPluginManagement command = AddMavenPluginManagement.builder().plugin(mavenEnforcerPluginManagement()).build();
      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command);
    }

    private String pluginManagement() {
      return """
              <plugin>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>${maven-enforcer-plugin.version}</version>
                <executions>
                  <execution>
                    <id>enforce-versions</id>
                    <goals>
                      <goal>enforce</goal>
                    </goals>
                  </execution>
                  <execution>
                    <id>enforce-dependencyConvergence</id>
                    <goals>
                      <goal>enforce</goal>
                    </goals>
                    <configuration>
                      <rules>
                        <DependencyConvergence />
                      </rules>
                      <fail>false</fail>
                    </configuration>
                  </execution>
                </executions>
                <configuration>
                  <rules>
                    <requireMavenVersion>
                      <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
                      <version>[${maven.version},)</version>
                    </requireMavenVersion>
                    <requireJavaVersion>
                      <message>You are running an incompatible version of Java. JHipster engine supports JDK 21+.</message>
                      <version>[21,22)</version>
                    </requireJavaVersion>
                  </rules>
                </configuration>
              </plugin>
            </plugins>
      """;
    }
  }

  @Nested
  @DisplayName("Add build plugin")
  class MavenCommandHandlerAddBuildPluginTest {

    @Test
    void shouldAddBuildPluginToEmptyPom() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(plugins());
    }

    @Test
    void shouldAddPropertyForPluginVersion() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom)
        .handle(AddDirectMavenPlugin.builder().javaBuildPlugin(mavenEnforcerPlugin()).pluginVersion(mavenEnforcerVersion()));

      assertThat(content(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
    }

    @Test
    void shouldAddBuildPluginToPomWithEmptyBuild() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(plugins())
        .doesNotContain(
          """
            <build>
            </build>
          """
        );
    }

    @Test
    void shouldAddBuildPluginToPomWithPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(plugins())
        .doesNotContain(
          """
              <plugins>
                <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
              </plugins>
          """
        );
    }

    @Test
    void shouldAddMinimalBuildPluginExecutionToPomWithPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(plugins())
        .doesNotContain(
          """
              <plugins>
                <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
              </plugins>
          """
        );
    }

    private void addMavenEnforcerPlugin(Path pom) {
      new MavenCommandHandler(Indentation.DEFAULT, pom)
        .handle(AddDirectMavenPlugin.builder().javaBuildPlugin(mavenEnforcerPlugin()).build());
    }

    private String plugins() {
      return """
            <plugin>
              <artifactId>maven-enforcer-plugin</artifactId>
            </plugin>
          </plugins>
      """;
    }
  }

  @Nested
  class AddBuildExtension {

    @Test
    void shouldAddBuildExtensionInPomWithoutBuild() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      assertThat(content(pom))
        .contains(
          """
              <extensions>
                <extension>
                  <groupId>kr.motd.maven</groupId>
                  <artifactId>os-maven-plugin</artifactId>
                  <version>${os-maven-plugin.version}</version>
                </extension>
              </extensions>
          """
        );
    }

    @Test
    void shouldAddBuildExtensionInPomWithoutExtensions() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
            <build>
              <extensions>
                <extension>
                  <groupId>kr.motd.maven</groupId>
                  <artifactId>os-maven-plugin</artifactId>
                  <version>${os-maven-plugin.version}</version>
                </extension>
              </extensions>
          """
        );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }

    @Test
    void shouldAddBuildExtensionInPomWithExtensions() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-extensions/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
            <build>
              <extensions>
                <extension>
                  <groupId>io.opentelemetry.contrib</groupId>
                  <artifactId>opentelemetry-maven-extension</artifactId>
                  <version>1.18.0</version>
                </extension>
                <extension>
                  <groupId>kr.motd.maven</groupId>
                  <artifactId>os-maven-plugin</artifactId>
                  <version>${os-maven-plugin.version}</version>
                </extension>
              </extensions>
          """
        );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }
  }

  @Test
  void addAddGradlePluginShouldNotBeHandled() {
    Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

    MavenCommandHandler commandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
    AddGradlePlugin command = AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).build();
    assertThatCode(() -> commandHandler.handle(command)).doesNotThrowAnyException();
  }

  private static Path projectWithPom(String sourcePom) {
    Path folder = Paths.get(TestFileUtils.tmpDirForTest());

    try {
      Files.createDirectories(folder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folder.resolve("pom.xml");
    try {
      Files.copy(Paths.get(sourcePom), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return pomPath;
  }

  private static String content(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
