package tech.jhipster.lite.module.domain.mavenplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectMavenPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectMavenPlugin.AddDirectMavenPluginOptionalBuilder;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenPluginManagement.AddMavenPluginManagementOptionalBuilder;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleMavenPlugins {

  private final Collection<MavenPlugin> pluginsManagement;
  private final Collection<MavenPlugin> plugins;

  private JHipsterModuleMavenPlugins(JHipsterModuleMavenPluginsBuilder builder) {
    pluginsManagement = builder.pluginsManagement;
    plugins = builder.plugins;
  }

  public static JHipsterModuleMavenPluginsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleMavenPluginsBuilder(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions) {
    Assert.notNull("versions", versions);

    Stream<JavaBuildCommand> managementCommands = pluginsManagement.stream().map(toAddMavenPluginManagement(versions));
    Stream<JavaBuildCommand> pluginsCommands = plugins.stream().map(toAddDirectMavenPlugin(versions));

    return new JavaBuildCommands(Stream.concat(managementCommands, pluginsCommands).toList());
  }

  private static Function<MavenPlugin, AddMavenPluginManagement> toAddMavenPluginManagement(JavaDependenciesVersions versions) {
    return plugin -> {
      AddMavenPluginManagementOptionalBuilder commandBuilder = AddMavenPluginManagement.builder().plugin(plugin);
      return plugin.versionSlug().map(versions::get).map(commandBuilder::pluginVersion).orElse(commandBuilder.build());
    };
  }

  private static Function<MavenPlugin, AddDirectMavenPlugin> toAddDirectMavenPlugin(JavaDependenciesVersions versions) {
    return plugin -> {
      AddDirectMavenPluginOptionalBuilder commandBuilder = AddDirectMavenPlugin.builder().javaBuildPlugin(plugin);
      return plugin.versionSlug().map(versions::get).map(commandBuilder::pluginVersion).orElse(commandBuilder.build());
    };
  }

  public static class JHipsterModuleMavenPluginsBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<MavenPlugin> pluginsManagement = new ArrayList<>();
    private final Collection<MavenPlugin> plugins = new ArrayList<>();

    private JHipsterModuleMavenPluginsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleMavenPluginsBuilder pluginManagement(MavenPlugin pluginManagement) {
      Assert.notNull("pluginManagement", pluginManagement);

      pluginsManagement.add(pluginManagement);

      return this;
    }

    public JHipsterModuleMavenPluginsBuilder plugin(MavenPlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleMavenPlugins build() {
      return new JHipsterModuleMavenPlugins(this);
    }
  }
}
