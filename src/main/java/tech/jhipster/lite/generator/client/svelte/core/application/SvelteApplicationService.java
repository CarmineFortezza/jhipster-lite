package tech.jhipster.lite.generator.client.svelte.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.common.domain.SvelteModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;

@Service
public class SvelteApplicationService {

  private final SvelteModuleFactory factory;

  public SvelteApplicationService() {
    this.factory = new SvelteModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties project) {
    return factory.buildSvelteModule(project);
  }
}
