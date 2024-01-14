package tech.jhipster.lite.module.domain;

public interface ContentReplacer {
  JHipsterProjectFilePath file();

  String apply(String content);

  void handleError(Throwable e);
}
