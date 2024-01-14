package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.shared.error.domain.Assert;

import java.nio.charset.StandardCharsets;

class JHipsterFileContent {

  private final JHipsterSource source;

  JHipsterFileContent(JHipsterSource source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public byte[] read(ProjectFiles files, JHipsterModuleContext context) {
    Assert.notNull("files", files);
    Assert.notNull("context", context);

    if (source.isNotTemplate()) {
      return files.readBytes(source.get().toString());
    }

    String rawContent = files.readString(source.get().toString());
    return ArgumentsReplacer.replaceArguments(rawContent, context.get()).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String toString() {
    return source.toString();
  }
}
