package tech.jhipster.lite.generator.server.springboot.broker.pulsar.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.PulsarModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.docker.DockerImages;

@Service
public class PulsarApplicationService {

  private final PulsarModuleFactory factory;

  public PulsarApplicationService(DockerImages dockerImages) {
    factory = new PulsarModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
