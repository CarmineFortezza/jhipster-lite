package tech.jhipster.lite.generator.server.webjars.infrastructure.primary;

import static tech.jhipster.lite.module.domain.resource.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.webjars.application.WebjarsApplicationService;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Configuration
class WebjarsModuleConfiguration {

  private static final String SERVER_TAG = "server";
  private static final String WEB_TAG = "web";
  private static final String WEBJARS_GROUP = "WebJars";

  @Bean
  public JHipsterModuleResource webjarsLocatorModule(WebjarsApplicationService webjarsModule) {
    return JHipsterModuleResource
      .builder()
      .slug(WEBJARS_LOCATOR)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add webjars locator to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjarsModule::buildWebjarsLocatorModule);
  }

  @Bean
  public JHipsterModuleResource webjarsHtmxModule(WebjarsApplicationService webjarsModule) {
    return JHipsterModuleResource
      .builder()
      .slug(HTMX_WEBJARS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add HTMX webjar to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjarsModule::buildWebjarsHtmxModule);
  }

  @Bean
  public JHipsterModuleResource webjarsAlpineJSModule(WebjarsApplicationService webjarsModule) {
    return JHipsterModuleResource
      .builder()
      .slug(ALPINE_JS_WEBJARS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add alpine.js webjar to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjarsModule::buildWebjarsAlpineJSModule);
  }
}
