package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginConfiguration;

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

  default MavenPluginOptionalBuilder addExecution(MavenPluginExecution.MavenPluginExecutionOptionalBuilder builder) {
    return addExecution(builder.build());
  }
}
