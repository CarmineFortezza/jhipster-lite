package tech.jhipster.lite.module.domain.mavenplugin;

import java.util.Collection;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record MavenPluginExecutions(Collection<MavenPluginExecution> executions) {
  public MavenPluginExecutions {
    Assert.notEmpty("executions", executions);
  }

  public Collection<MavenPluginExecution> get() {
    return executions;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return executions.toString();
  }
}
