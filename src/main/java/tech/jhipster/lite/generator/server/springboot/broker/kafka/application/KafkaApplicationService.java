package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.docker.DockerImages;

@Service
public class KafkaApplicationService {

  private final KafkaModuleFactory kafkaModuleFactory;

  public KafkaApplicationService(final DockerImages dockerImages) {
    this.kafkaModuleFactory = new KafkaModuleFactory(dockerImages);
  }

  public JHipsterModule init(final JHipsterModuleProperties properties) {
    return kafkaModuleFactory.buildModuleInit(properties);
  }

  public JHipsterModule addDummyProducerConsumer(final JHipsterModuleProperties properties) {
    return kafkaModuleFactory.buildModuleDummyProducerConsumer(properties);
  }

  public JHipsterModule addAkhq(final JHipsterModuleProperties properties) {
    return kafkaModuleFactory.buildModuleAkhq(properties);
  }
}
