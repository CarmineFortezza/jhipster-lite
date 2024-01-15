package tech.jhipster.lite.module.domain.gradleplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import tech.jhipster.lite.module.domain.AddGradlePlugin;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.AddGradlePlugin.AddGradlePluginOptionalBuilder;
import tech.jhipster.lite.module.domain.JavaBuildCommand;
import tech.jhipster.lite.module.domain.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleGradlePlugin {

  private final Collection<GradlePlugin> plugins;

  private JHipsterModuleGradlePlugin(JHipsterModuleGradlePluginBuilder builder) {
    Assert.notNull("plugins", builder.plugins);
    plugins = builder.plugins;
  }

  public static JHipsterModuleGradlePluginBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleGradlePluginBuilder(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions) {
    Assert.notNull("versions", versions);

    return new JavaBuildCommands(plugins.stream().map(toCommands(versions)).toList());
  }

  private Function<GradlePlugin, JavaBuildCommand> toCommands(JavaDependenciesVersions versions) {
    return plugin ->
      switch (plugin) {
        case GradleCorePlugin corePlugin -> mapCorePlugin(corePlugin, versions);
        case GradleCommunityPlugin gradleCommunityPlugin -> mapCommunityPlugin(gradleCommunityPlugin, versions);
      };
  }

  private JavaBuildCommand mapCorePlugin(GradleCorePlugin plugin, JavaDependenciesVersions versions) {
    AddGradlePluginOptionalBuilder commandBuilder = AddGradlePlugin.builder().plugin(plugin);
    plugin.toolVersionSlug().map(versions::get).ifPresent(commandBuilder::toolVersion);
    return commandBuilder.build();
  }

  private JavaBuildCommand mapCommunityPlugin(GradleCommunityPlugin plugin, JavaDependenciesVersions versions) {
    AddGradlePluginOptionalBuilder commandBuilder = AddGradlePlugin.builder().plugin(plugin);
    plugin.versionSlug().map(versions::get).ifPresent(commandBuilder::pluginVersion);
    return commandBuilder.build();
  }

  public static class JHipsterModuleGradlePluginBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<GradlePlugin> plugins = new ArrayList<>();

    private JHipsterModuleGradlePluginBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleGradlePluginBuilder plugin(GradlePlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleGradlePlugin build() {
      return new JHipsterModuleGradlePlugin(this);
    }
  }
}
