package tech.jhipster.lite.module.application;

import java.util.Collection;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.JHipsterModulesToApply;
import tech.jhipster.lite.module.domain.git.GitRepository;
import tech.jhipster.lite.module.domain.javabuild.command.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscape;
import tech.jhipster.lite.module.domain.landscape.JHipsterModulesApplyer;
import tech.jhipster.lite.module.domain.landscape.JHipsterModulesRepository;
import tech.jhipster.lite.module.domain.landscape.JHipsterModulesResources;

@Service
public class JHipsterModulesApplicationService {

  private final JHipsterModuleEvents events;
  private final JHipsterModulesRepository modules;
  private final JHipsterModulesApplyer applyer;

  public JHipsterModulesApplicationService(
    JHipsterModuleEvents events,
    JHipsterModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    GitRepository git,
    GeneratedProjectRepository generatedProject
  ) {
    this.events = events;
    this.modules = modules;

    applyer = new JHipsterModulesApplyer(modules, currentVersions, projectDependencies, git, generatedProject);
  }

  public void apply(JHipsterModulesToApply modulesToApply) {
    Collection<JHipsterModuleApplied> modulesApplied = applyer.apply(modulesToApply);

    modulesApplied.forEach(events::dispatch);
  }

  public void apply(JHipsterModuleToApply moduleToApply) {
    JHipsterModuleApplied moduleApplied = applyer.apply(moduleToApply);

    events.dispatch(moduleApplied);
  }

  public JHipsterModulesResources resources() {
    return modules.resources();
  }

  public JHipsterLandscape landscape() {
    return modules.landscape();
  }
}
