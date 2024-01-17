package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;

import java.util.List;
import java.util.Map;

public interface FileSystemJHipsterModulesRepositoryCD {

  String DEFAULT_MAIN_FOLDER = "src/main/resources/config/";
  String DEFAULT_TEST_FOLDER = "src/test/resources/config/";

  static Map<SpringPropertyType, List<String>> buildPaths() {
    return Map.of(
      SpringPropertyType.MAIN_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.TEST_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/"),
      SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/")
    );
  }
}
