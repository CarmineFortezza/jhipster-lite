package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.javabuild.GroupId;

public interface MavenPluginGroupIdBuilder {
  MavenPluginArtifactIdBuilder groupId(GroupId groupId);

  default MavenPluginArtifactIdBuilder groupId(String groupId) {
    return groupId(new GroupId(groupId));
  }
}
