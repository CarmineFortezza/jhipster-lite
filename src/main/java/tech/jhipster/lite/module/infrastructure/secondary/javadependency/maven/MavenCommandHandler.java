package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.maven.model.Activation;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Extension;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Profile;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.buildproperties.BuildProperty;
import tech.jhipster.lite.module.domain.buildproperties.PropertyKey;
import tech.jhipster.lite.module.domain.buildproperties.PropertyValue;
import tech.jhipster.lite.module.domain.javabuild.MavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddGradlePlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetBuildProperty;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileActivation;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileId;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyClassifier;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.mavenplugin.MavenBuildPhase;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginConfiguration;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecution;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecutionGoal;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecutionId;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecutions;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesCommandHandler;
import tech.jhipster.lite.shared.enumeration.domain.Enums;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class MavenCommandHandler implements JavaDependenciesCommandHandler {

  private static final MavenXpp3Writer mavenWriter = new MavenXpp3Writer();
  private static final String COMMAND = "command";
  private static final int DEFAULT_MAVEN_INDENTATION = 2;

  private final Indentation indentation;
  private final Path pomPath;
  private final Model pomModel;

  public MavenCommandHandler(Indentation indentation, Path pomPath) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("pomPath", pomPath);

    this.indentation = indentation;
    this.pomPath = pomPath;
    pomModel = readModel(pomPath);
  }

  private Model readModel(Path pomPath) {
    try (InputStream input = Files.newInputStream(pomPath)) {
      MavenXpp3Reader reader = new MavenXpp3Reader();
      return reader.read(input);
    } catch (IOException | XmlPullParserException e) {
      throw GeneratorException.technicalError("Error reading pom file: " + e.getMessage(), e);
    }
  }

  @Override
  public void handle(SetVersion command) {
    Assert.notNull(COMMAND, command);

    BuildProperty property = new BuildProperty(new PropertyKey(command.property()), new PropertyValue(command.dependencyVersion()));
    handle(new SetBuildProperty(property));
  }

  @Override
  public void handle(SetBuildProperty command) {
    Assert.notNull(COMMAND, command);

    if (command.buildProfile().isPresent()) {
      BuildProfileId buildProfileId = command.buildProfile().orElseThrow();
      Profile mavenProfile = pomModel
        .getProfiles()
        .stream()
        .filter(profileMatch(buildProfileId))
        .findFirst()
        .orElseThrow(() -> new MissingMavenProfileException(buildProfileId));
      mavenProfile.addProperty(command.property().key().get(), command.property().value().get());
    } else {
      pomModel.addProperty(command.property().key().get(), command.property().value().get());
    }

    writePom();
  }

  private static Predicate<Profile> profileMatch(BuildProfileId buildProfileId) {
    return profile -> profile.getId().equals(buildProfileId.value());
  }

  @Override
  public void handle(AddJavaBuildProfile command) {
    Assert.notNull(COMMAND, command);

    List<Profile> profiles = pomModel.getProfiles();
    if (profiles.stream().noneMatch(profileMatch(command.buildProfileId()))) {
      Profile profile = toMavenProfile(command);
      pomModel.addProfile(profile);
    }

    writePom();
  }

  private static Profile toMavenProfile(AddJavaBuildProfile command) {
    Profile profile = new Profile();
    profile.setId(command.buildProfileId().value());
    command.activation().ifPresent(activation -> profile.setActivation(toMavenActivation(activation)));

    return profile;
  }

  private static Activation toMavenActivation(BuildProfileActivation activation) {
    Activation mavenActivation = new Activation();
    activation.activeByDefault().ifPresent(mavenActivation::setActiveByDefault);
    return mavenActivation;
  }

  @Override
  public void handle(RemoveJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    DependencyManagement dependenciesManagement = pomModel.getDependencyManagement();
    if (dependenciesManagement != null) {
      removeDependencyFrom(command.dependency(), dependenciesManagement.getDependencies());
    }
  }

  @Override
  public void handle(RemoveDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    removeDependencyFrom(command.dependency(), pomModel.getDependencies());
  }

  private void removeDependencyFrom(DependencyId dependency, List<Dependency> dependencies) {
    dependencies.removeIf(dependencyMatch(dependency));
    writePom();
  }

  private Predicate<Dependency> dependencyMatch(DependencyId dependencyId) {
    return mavenDependency -> {
      boolean sameGroupId = mavenDependency.getGroupId().equals(dependencyId.groupId().get());
      boolean sameArtifactId = mavenDependency.getArtifactId().equals(dependencyId.artifactId().get());

      return sameGroupId && sameArtifactId;
    };
  }

  @Override
  public void handle(AddMavenBuildExtension command) {
    Assert.notNull(COMMAND, command);

    projectBuild().addExtension(toMavenExtension(command.buildExtension()));

    writePom();
  }

  private Build projectBuild() {
    if (pomModel.getBuild() == null) {
      pomModel.setBuild(new Build());
    }
    return pomModel.getBuild();
  }

  private static Extension toMavenExtension(MavenBuildExtension mavenBuildExtension) {
    Extension extension = new Extension();
    extension.setArtifactId(mavenBuildExtension.artifactId().get());
    extension.setGroupId(mavenBuildExtension.groupId().get());
    mavenBuildExtension.versionSlug().map(VersionSlug::mavenVariable).ifPresent(extension::setVersion);
    return extension;
  }

  @Override
  public void handle(AddJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    addDependencyTo(command, dependencyManagement().getDependencies());
  }

  private DependencyManagement dependencyManagement() {
    if (pomModel.getDependencyManagement() == null) {
      pomModel.setDependencyManagement(new DependencyManagement());
    }
    return pomModel.getDependencyManagement();
  }

  @Override
  public void handle(AddDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    addDependencyTo(command, pomModel.getDependencies());
  }

  private void addDependencyTo(AddJavaDependency command, List<Dependency> dependencies) {
    if (command.scope() == JavaDependencyScope.TEST) {
      dependencies.add(toMavenDependency(command));
    } else {
      Dependency mavenDependency = toMavenDependency(command);
      insertDependencyBeforeFirstTestDependency(mavenDependency, dependencies);
    }

    writePom();
  }

  private void insertDependencyBeforeFirstTestDependency(Dependency mavenDependency, List<Dependency> dependencies) {
    List<Dependency> nonTestDependencies = dependencies
      .stream()
      .filter(dependency -> !MavenScope.TEST.key().equals(dependency.getScope()))
      .toList();
    if (nonTestDependencies.isEmpty()) {
      dependencies.add(mavenDependency);
    } else {
      dependencies.add(dependencies.indexOf(nonTestDependencies.getLast()) + 1, mavenDependency);
    }
  }

  private Dependency toMavenDependency(AddJavaDependency command) {
    Dependency mavenDependency = new Dependency();
    mavenDependency.setGroupId(command.dependencyId().groupId().get());
    mavenDependency.setArtifactId(command.dependencyId().artifactId().get());
    command.version().map(VersionSlug::mavenVariable).ifPresent(mavenDependency::setVersion);
    command.classifier().map(JavaDependencyClassifier::get).ifPresent(mavenDependency::setClassifier);
    command.dependencyType().map(type -> Enums.map(type, MavenType.class)).map(MavenType::key).ifPresent(mavenDependency::setType);
    command.exclusions().stream().map(toMavenExclusion()).forEach(mavenDependency::addExclusion);

    if (command.scope() != JavaDependencyScope.COMPILE) {
      mavenDependency.setScope(Enums.map(command.scope(), MavenScope.class).key());
    }
    if (command.optional()) {
      mavenDependency.setOptional(true);
    }

    return mavenDependency;
  }

  private Function<DependencyId, Exclusion> toMavenExclusion() {
    return dependencyId -> {
      Exclusion mavenExclusion = new Exclusion();
      mavenExclusion.setGroupId(dependencyId.groupId().get());
      mavenExclusion.setArtifactId(dependencyId.artifactId().get());
      return mavenExclusion;
    };
  }

  @Override
  public void handle(AddMavenPluginManagement command) {
    Assert.notNull(COMMAND, command);

    pluginManagement().addPlugin(toMavenPlugin(command));
    command.pluginVersion().ifPresent(version -> handle(new SetVersion(version)));

    writePom();
  }

  private PluginManagement pluginManagement() {
    if (projectBuild().getPluginManagement() == null) {
      projectBuild().setPluginManagement(new PluginManagement());
    }
    return projectBuild().getPluginManagement();
  }

  @Override
  public void handle(AddDirectMavenPlugin command) {
    Assert.notNull(COMMAND, command);

    projectBuild().addPlugin(toMavenPlugin(command));
    command.pluginVersion().ifPresent(version -> handle(new SetVersion(version)));

    writePom();
  }

  @Override
  public void handle(AddGradlePlugin command) {
    // Gradle commands are ignored
  }

  private Plugin toMavenPlugin(AddMavenPlugin command) {
    Plugin mavenPlugin = new Plugin();
    mavenPlugin.setArtifactId(command.dependencyId().artifactId().get());
    mavenPlugin.setGroupId(command.dependencyId().groupId().get());
    command.versionSlug().map(VersionSlug::mavenVariable).ifPresent(mavenPlugin::setVersion);
    command.configuration().map(toMavenConfiguration()).ifPresent(mavenPlugin::setConfiguration);
    command
      .executions()
      .stream()
      .map(MavenPluginExecutions::get)
      .flatMap(Collection::stream)
      .map(toMavenExecution())
      .forEach(mavenPlugin::addExecution);
    return mavenPlugin;
  }

  private Function<MavenPluginExecution, PluginExecution> toMavenExecution() {
    return execution -> {
      PluginExecution mavenExecution = new PluginExecution();
      execution.id().map(MavenPluginExecutionId::get).ifPresent(mavenExecution::setId);
      execution.phase().map(MavenBuildPhase::mavenKey).ifPresent(mavenExecution::setPhase);
      execution.goals().stream().map(MavenPluginExecutionGoal::get).forEach(mavenExecution::addGoal);
      execution.configuration().map(toMavenConfiguration()).ifPresent(mavenExecution::setConfiguration);
      return mavenExecution;
    };
  }

  private Function<MavenPluginConfiguration, Xpp3Dom> toMavenConfiguration() {
    return configuration -> {
      try (Reader reader = new StringReader("<configuration>" + configuration.get() + "</configuration>")) {
        return Xpp3DomBuilder.build(reader);
      } catch (XmlPullParserException | IOException e) {
        throw new MalformedAdditionalInformationException(e);
      }
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The exception handling is hard to test and an implementation detail")
  private void writePom() {
    try (Writer fileWriter = Files.newBufferedWriter(pomPath, StandardCharsets.UTF_8)) {
      StringWriter stringWriter = new StringWriter();
      mavenWriter.write(stringWriter, pomModel);
      fileWriter.write(applyIndentation(stringWriter.getBuffer().toString()));
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error writing pom: " + e.getMessage(), e);
    }
  }

  private String applyIndentation(String pomContent) {
    if (indentation.spacesCount() == DEFAULT_MAVEN_INDENTATION) {
      return pomContent;
    }
    return pomContent.replace(" ".repeat(DEFAULT_MAVEN_INDENTATION), indentation.spaces());
  }
}
