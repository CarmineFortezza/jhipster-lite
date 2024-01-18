package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MavenPluginBuilder implements MavenPluginGroupIdBuilder, MavenPluginArtifactIdBuilder, MavenPluginOptionalBuilder
{
  private GroupId groupId;
  private ArtifactId artifactId;
  private VersionSlug versionSlug;
  private MavenPluginConfiguration configuration;
  private final List<MavenPluginExecution> executions = new ArrayList<>();

  public MavenPluginBuilder() {}

  public GroupId getGroupId() {
    return groupId;
  }

  public ArtifactId getArtifactId() {
    return artifactId;
  }

  public VersionSlug getVersionSlug() {
    return versionSlug;
  }

  public MavenPluginConfiguration getConfiguration() {
    return configuration;
  }

  public List<MavenPluginExecution> getExecutions() {
    return executions;
  }

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
