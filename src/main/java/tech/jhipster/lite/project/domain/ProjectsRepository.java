package tech.jhipster.lite.project.domain;

import tech.jhipster.lite.project.domain.download.Project;

import java.util.Optional;

public interface ProjectsRepository {
  void format(ProjectPath path);

  Optional<Project> get(ProjectPath path);

  void save(ProjectHistory history);

  ProjectHistory getHistory(ProjectPath path);
}
