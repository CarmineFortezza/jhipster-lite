package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.domain.CassandraMigrationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.docker.DockerImages;

@Service
public class CassandraMigrationApplicationService {

  private final CassandraMigrationModuleFactory factory;

  public CassandraMigrationApplicationService(DockerImages dockerImages) {
    factory = new CassandraMigrationModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
