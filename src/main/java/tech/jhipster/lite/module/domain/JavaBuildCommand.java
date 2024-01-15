package tech.jhipster.lite.module.domain;

public sealed interface JavaBuildCommand
  permits
    AddMavenPluginManagement,
    AddDirectMavenPlugin,
        AddDirectJavaDependency,
  AddGradlePlugin,
  AddJavaBuildProfile,
        AddJavaDependencyManagement,
        AddMavenBuildExtension,
    RemoveDirectJavaDependency,
    RemoveJavaDependencyManagement,
  SetBuildProperty,
    SetVersion {}
