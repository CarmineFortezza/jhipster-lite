package tech.jhipster.lite.module.domain.javabuild.command;

import java.util.Optional;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.shared.error.domain.Assert;

public final class AddDirectMavenPlugin implements JavaBuildCommand, AddMavenPlugin {

  private final MavenPlugin plugin;
  private final Optional<JavaDependencyVersion> pluginVersion;

  private AddDirectMavenPlugin(AddDirectMavenPluginBuilder builder) {
    Assert.notNull("plugin", builder.plugin);
    this.plugin = builder.plugin;
    this.pluginVersion = Optional.ofNullable(builder.pluginVersion);
  }

  @Override
  public MavenPlugin plugin() {
    return plugin;
  }

  @Override
  public Optional<JavaDependencyVersion> pluginVersion() {
    return pluginVersion;
  }

  public static AddDirectMavenPluginPluginBuilder builder() {
    return new AddDirectMavenPluginBuilder();
  }

  private static class AddDirectMavenPluginBuilder implements AddDirectMavenPluginPluginBuilder, AddDirectMavenPluginOptionalBuilder {

    private MavenPlugin plugin;
    private JavaDependencyVersion pluginVersion;

    private AddDirectMavenPluginBuilder() {}

    @Override
    public AddDirectMavenPluginBuilder javaBuildPlugin(MavenPlugin plugin) {
      this.plugin = plugin;
      return this;
    }

    @Override
    public AddDirectMavenPlugin pluginVersion(JavaDependencyVersion pluginVersion) {
      this.pluginVersion = pluginVersion;
      return build();
    }

    public AddDirectMavenPlugin build() {
      return new AddDirectMavenPlugin(this);
    }
  }

  public interface AddDirectMavenPluginPluginBuilder {
    AddDirectMavenPluginOptionalBuilder javaBuildPlugin(MavenPlugin javaBuildPlugin);
  }

  public interface AddDirectMavenPluginOptionalBuilder {
    AddDirectMavenPlugin pluginVersion(JavaDependencyVersion version);
    AddDirectMavenPlugin build();
  }
}
