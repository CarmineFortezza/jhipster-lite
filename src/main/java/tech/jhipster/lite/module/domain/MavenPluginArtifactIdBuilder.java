package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javabuild.ArtifactId;

public interface MavenPluginArtifactIdBuilder {

  MavenPluginOptionalBuilder artifactId(ArtifactId artifactId);

  default MavenPluginOptionalBuilder artifactId(String artifactId) {
    return artifactId(new ArtifactId(artifactId));
  }
}
