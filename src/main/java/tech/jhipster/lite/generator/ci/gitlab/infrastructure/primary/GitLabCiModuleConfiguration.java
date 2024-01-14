package tech.jhipster.lite.generator.ci.gitlab.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.gitlab.application.GitLabCiApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;

@Configuration
class GitLabCiModuleConfiguration {

  @Bean
  JHipsterModuleResource gitLabCiModule(GitLabCiApplicationService gitlabCi) {
    return JHipsterModuleResource
      .builder()
      .slug(GITLAB_CI)
      .withoutProperties()
      .apiDoc("Continuous Integration", "Add GitLab CI for Maven Build")
      .organization(JHipsterModuleOrganization.builder().addDependency(MAVEN_JAVA).build())
      .tags("ci", "gitlab")
      .factory(gitlabCi::buildModule);
  }
}
