package tech.jhipster.lite.module.domain.properties;

import java.util.Map;
import java.util.function.Predicate;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleParameters(Map<String, Object> parameters) {
  public JHipsterModuleParameters(Map<String, Object> parameters) {
    this.parameters = JHipsterCollections.immutable(parameters);
  }

  public <T> T getOrDefault(String key, T defaultValue, Class<T> clazz) {
    return getOrDefault(key, defaultValue, clazz, t -> false);
  }

  public <T> T getOrDefault(String key, T defaultValue, Class<T> clazz, Predicate<T> isEmpty) {
    Assert.notBlank("key", key);

    if (!parameters.containsKey(key)) {
      return defaultValue;
    }

    T value = get(key, clazz);

    if (isEmpty.test(value)) {
      return defaultValue;
    }

    return value;
  }

  public <T> T get(String key, Class<T> clazz) {
    Assert.notBlank("key", key);

    Object property = parameters.get(key);

    if (property == null) {
      throw new UnknownPropertyException(key);
    }

    if (clazz.isInstance(property)) {
      return clazz.cast(property);
    }

    throw InvalidPropertyTypeException.builder().key(key).expectedType(clazz).actualType(property.getClass());
  }

  public Map<String, Object> get() {
    return parameters();
  }
}
