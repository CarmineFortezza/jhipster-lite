package tech.jhipster.lite.module.domain.landscape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;
import tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug;
import tech.jhipster.lite.module.domain.resource.JHipsterFeatureSlugFactory;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleSlugFactory;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleOrganization {

  public static final JHipsterModuleOrganization STANDALONE = builder().build();
  public static final JHipsterModuleOrganization SPRINGBOOT_DEPENDENCY = builder().addDependency(JHLiteModuleSlug.SPRING_BOOT).build();

  private final Optional<JHipsterFeatureSlug> feature;
  private final Collection<JHipsterLandscapeDependency> dependencies;

  private JHipsterModuleOrganization(JHipsterModuleOrganizationBuilder builder) {
    feature = builder.feature;
    dependencies = builder.dependencies;
  }

  public static JHipsterModuleOrganizationBuilder builder() {
    return new JHipsterModuleOrganizationBuilder();
  }

  public Optional<JHipsterFeatureSlug> feature() {
    return feature;
  }

  public Collection<JHipsterLandscapeDependency> dependencies() {
    return dependencies;
  }

  public static class JHipsterModuleOrganizationBuilder {

    private final Collection<JHipsterLandscapeDependency> dependencies = new ArrayList<>();
    private Optional<JHipsterFeatureSlug> feature = Optional.empty();

    public JHipsterModuleOrganizationBuilder feature(JHipsterFeatureSlugFactory feature) {
      Assert.notNull("feature", feature);

      this.feature = feature.build();

      return this;
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterModuleSlugFactory module) {
      Assert.notNull("module", module);

      dependencies.add(new JHipsterModuleDependency(module.build()));

      return this;
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterFeatureSlugFactory feature) {
      Assert.notNull("feature", feature);

      feature.build().map(JHipsterFeatureDependency::new).ifPresent(dependencies::add);

      return this;
    }

    public JHipsterModuleOrganization build() {
      return new JHipsterModuleOrganization(this);
    }
  }
}
