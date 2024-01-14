package tech.jhipster.lite.module.domain.mavenplugin;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecution.MavenPluginExecutionOptionalBuilder;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class MavenPlugin {

  private final DependencyId dependencyId;
  private final Optional<VersionSlug> versionSlug;
  private final Optional<MavenPluginConfiguration> configuration;
  private final Optional<MavenPluginExecutions> executions;

  private MavenPlugin(MavenPluginBuilder builder) {
    dependencyId = DependencyId.of(builder.groupId, builder.artifactId);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    configuration = Optional.ofNullable(builder.configuration);
    executions = Optional.ofNullable(builder.executions).filter(not(Collection::isEmpty)).map(MavenPluginExecutions::new);
  }

  public static MavenPluginGroupIdBuilder builder() {
    return new MavenPluginBuilder();
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public Optional<MavenPluginConfiguration> configuration() {
    return configuration;
  }

  public Optional<MavenPluginExecutions> executions() {
    return executions;
  }

  public DependencyId dependencyId() {
    return dependencyId;
  }

  private static class MavenPluginBuilder implements MavenPluginGroupIdBuilder, MavenPluginArtifactIdBuilder, MavenPluginOptionalBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private VersionSlug versionSlug;
    private MavenPluginConfiguration configuration;
    private final List<MavenPluginExecution> executions = new ArrayList<>();

    private MavenPluginBuilder() {}

    @Override
    public MavenPluginArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder configuration(MavenPluginConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    @Override
    public MavenPluginOptionalBuilder addExecution(MavenPluginExecution executions) {
      this.executions.add(executions);
      return this;
    }

    @Override
    public MavenPlugin build() {
      return new MavenPlugin(this);
    }
  }

  public interface MavenPluginGroupIdBuilder {
    MavenPluginArtifactIdBuilder groupId(GroupId groupId);

    default MavenPluginArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface MavenPluginArtifactIdBuilder {
    MavenPluginOptionalBuilder artifactId(ArtifactId artifactId);

    default MavenPluginOptionalBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface MavenPluginOptionalBuilder {
    MavenPluginOptionalBuilder versionSlug(VersionSlug slug);

    MavenPlugin build();

    default MavenPluginOptionalBuilder versionSlug(String slug) {
      return versionSlug(new VersionSlug(slug));
    }

    MavenPluginOptionalBuilder configuration(MavenPluginConfiguration configuration);

    default MavenPluginOptionalBuilder configuration(String configuration) {
      return configuration(new MavenPluginConfiguration(configuration));
    }

    MavenPluginOptionalBuilder addExecution(MavenPluginExecution executions);

    default MavenPluginOptionalBuilder addExecution(MavenPluginExecutionOptionalBuilder builder) {
      return addExecution(builder.build());
    }
  }

  @Override
  @SuppressWarnings("java:S1192")
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("dependencyId", dependencyId)
      .append("versionSlug", versionSlug.map(VersionSlug::toString).orElse("(empty)"))
      .append("configuration", configuration.map(MavenPluginConfiguration::toString).orElse("(empty)"))
      .append("executions", executions.map(MavenPluginExecutions::toString).orElse("(empty)"));
    return builder.toString();
  }
}
