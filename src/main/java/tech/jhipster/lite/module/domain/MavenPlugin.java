package tech.jhipster.lite.module.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginConfiguration;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecutions;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

import java.util.Collection;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class MavenPlugin {

  private final DependencyId dependencyId;
  private final Optional<VersionSlug> versionSlug;
  private final Optional<MavenPluginConfiguration> configuration;
  private final Optional<MavenPluginExecutions> executions;

  public MavenPlugin(MavenPluginBuilder builder) {
    dependencyId = DependencyId.of(builder.getGroupId(), builder.getArtifactId());
    versionSlug = Optional.ofNullable(builder.getVersionSlug());
    configuration = Optional.ofNullable(builder.getConfiguration());
    executions = Optional.ofNullable(builder.getExecutions()).filter(not(Collection::isEmpty)).map(MavenPluginExecutions::new);
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
