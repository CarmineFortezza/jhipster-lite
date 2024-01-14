package tech.jhipster.lite.module.domain.javabuildprofile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.buildproperties.BuildProperty;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.SetBuildProperty;
import tech.jhipster.lite.module.domain.javabuildprofile.JHipsterModuleJavaBuildProfile.JHipsterModuleJavaBuildProfileBuilder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleJavaBuildProfiles {

  private final Collection<JHipsterModuleJavaBuildProfile> profiles;

  private JHipsterModuleJavaBuildProfiles(JHipsterModuleJavaBuildProfilesBuilder builder) {
    this.profiles = builder.profiles.values().stream().map(JHipsterModuleJavaBuildProfileBuilder::build).toList();
  }

  public static JHipsterModuleJavaBuildProfilesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleJavaBuildProfilesBuilder(module);
  }

  public JavaBuildCommands buildChanges() {
    Stream<JavaBuildCommand> addProfileCommands = profiles.stream().map(toAddProfileCommands());
    Stream<JavaBuildCommand> addPropertyCommands = profiles.stream().flatMap(toAddPropertyCommands());

    return new JavaBuildCommands(Stream.concat(addProfileCommands, addPropertyCommands).toList());
  }

  private Function<JHipsterModuleJavaBuildProfile, JavaBuildCommand> toAddProfileCommands() {
    return profile -> new AddJavaBuildProfile(profile.id(), profile.activation());
  }

  private Function<JHipsterModuleJavaBuildProfile, Stream<JavaBuildCommand>> toAddPropertyCommands() {
    return profile ->
      profile
        .properties()
        .entrySet()
        .stream()
        .map(property -> new SetBuildProperty(new BuildProperty(property.getKey(), property.getValue()), profile.id()));
  }

  public static class JHipsterModuleJavaBuildProfilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<BuildProfileId, JHipsterModuleJavaBuildProfileBuilder> profiles = new HashMap<>();

    private JHipsterModuleJavaBuildProfilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaBuildProfileBuilder addProfile(BuildProfileId buildProfileId) {
      Assert.notNull("buildProfileId", buildProfileId);

      return profiles.computeIfAbsent(buildProfileId, id -> JHipsterModuleJavaBuildProfile.builder(this, buildProfileId));
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleJavaBuildProfiles build() {
      return new JHipsterModuleJavaBuildProfiles(this);
    }
  }
}
