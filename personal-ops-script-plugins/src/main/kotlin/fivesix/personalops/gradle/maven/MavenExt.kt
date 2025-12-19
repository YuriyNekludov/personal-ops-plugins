package fivesix.personalops.gradle.maven

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.gitHubRepository(project: Project): MavenArtifactRepository {
    val urlProvider = project.providers.gradleProperty("url")
        .orElse(project.providers.environmentVariable("MAVEN_URL"))
    val usernameProvider = project.providers.gradleProperty("username")
        .orElse(project.providers.environmentVariable("MAVEN_USERNAME"))
    val passwordProvider = project.providers.gradleProperty("password")
        .orElse(project.providers.environmentVariable("MAVEN_PASSWORD"))

    return maven(urlProvider.get()) {
        credentials {
            this.username = usernameProvider.get()
            this.password = passwordProvider.get()
        }
    }
}