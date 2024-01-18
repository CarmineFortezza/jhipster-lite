package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.ReplacementCondition;
import tech.jhipster.lite.shared.error.domain.Assert;

import java.util.function.BiFunction;

public record TextNeedleAfterReplacer(ReplacementCondition condition, String text) implements ElementReplacer {

  public static final String LINE_BREAK = "\n";
  public TextNeedleAfterReplacer {
    Assert.notNull("condition", condition);
    Assert.notBlank("text", text);
  }

  @Override
  public boolean notMatchIn(String content) {
    Assert.notNull("content", content);

    return !content.contains(text());
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> {
      String replacementBlock = LINE_BREAK + replacement;
      return content.replaceAll(this.text, this.text + replacementBlock);
    };
  }

  @Override
  public String searchMatcher() {
    return text();
  }
}
