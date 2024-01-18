package tech.jhipster.lite.generator.server.javatool.checkstyle.domain;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.MavenPlugin;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.gradleplugin.GradlePlugin;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.shared.error.domain.Assert;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.mavenplugin.MavenBuildPhase.VALIDATE;

public class CheckstyleModuleFactory {

  private static final JHipsterSource TEMPLATES_SOURCE = from("server/javatool/checkstyle/main");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(checkstyleMavenPlugin())
        .and()
      .gradlePlugins()
        .plugin(checkstyleGradlePlugin())
        .and()
      .files()
        .add(TEMPLATES_SOURCE.template("checkstyle.xml"), to("checkstyle.xml"))
        .and()
      .build();
    //@formatter:on
  }

  private MavenPlugin checkstyleMavenPlugin() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-checkstyle-plugin")
      .versionSlug("maven-checkstyle-plugin")
      .configuration(
        """
        <configLocation>checkstyle.xml</configLocation>
        <includeTestSourceDirectory>true</includeTestSourceDirectory>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
        """
      )
      .addExecution(pluginExecution().goals("check").id("validate").phase(VALIDATE))
      .build();
  }

  private GradlePlugin checkstyleGradlePlugin() {
    VersionSlug toolVersionSlug = new VersionSlug("checkstyle");
    return gradleCorePlugin()
      .id("checkstyle")
      .toolVersionSlug(toolVersionSlug)
      .configuration(
        """
        checkstyle {
          configFile = rootProject.file("checkstyle.xml")
          toolVersion = libs.versions.%s.get()
        }

        // Workaround for https://github.com/gradle/gradle/issues/27035
        configurations.checkstyle {
          resolutionStrategy.capabilitiesResolution.withCapability("com.google.collections:google-collections") {
            select("com.google.guava:guava:0")
          }
        }
        """.formatted(toolVersionSlug.slug())
      )
      .build();
  }
}
