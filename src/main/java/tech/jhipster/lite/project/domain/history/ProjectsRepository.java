package tech.jhipster.lite.project.domain.history;

import java.util.Optional;

import tech.jhipster.lite.project.domain.ProjectHistory;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.download.Project;

public interface ProjectsRepository {
  void format(ProjectPath path);

  Optional<Project> get(ProjectPath path);

  void save(ProjectHistory history);

  ProjectHistory getHistory(ProjectPath path);
}
