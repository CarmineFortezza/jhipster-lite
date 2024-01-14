package tech.jhipster.lite.generator.server.javatool.checkstyle.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.server.javatool.checkstyle.domain.CheckstyleModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;

@Component
public class CheckstyleApplicationService {

  private final CheckstyleModuleFactory factory;

  public CheckstyleApplicationService() {
    factory = new CheckstyleModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
