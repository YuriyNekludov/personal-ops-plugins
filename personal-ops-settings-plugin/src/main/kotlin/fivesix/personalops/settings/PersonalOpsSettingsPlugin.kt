package fivesix.personalops.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.time.Duration

class PersonalOpsSettingsPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        val platformGroup = "fivesix.personalops"
        val platformArtifact = "personal-ops-platform"
        val platformVersion = resolvePlatformVersion(target)

        target.dependencyResolutionManagement {
            versionCatalogs {
                create("libs") { from("$platformGroup:$platformArtifact:$platformVersion") }
            }
        }

        target.gradle.beforeProject {
            val opsPlatform = configurations.create("opsPlatform").apply {
                isCanBeResolved = false
                isCanBeConsumed = false
                withDependencies {
                    add(project.dependencies.platform("$platformGroup:$platformArtifact:$platformVersion"))
                }
            }

            plugins.withType<JavaPlugin> {
                extensions.getByType<JavaPluginExtension>().sourceSets.configureEach {
                    configurations.named(compileClasspathConfigurationName).configure { extendsFrom(opsPlatform) }
                    configurations.named(runtimeClasspathConfigurationName).configure { extendsFrom(opsPlatform) }
                    configurations.named(annotationProcessorConfigurationName).configure { extendsFrom(opsPlatform) }
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.kapt") {
                configurations.matching { it.name.startsWith("kapt") }.configureEach {
                    extendsFrom(opsPlatform)
                }
            }
        }
    }

    private fun resolvePlatformVersion(target: Settings): String {
        target.providers.gradleProperty("platformVersion").orNull?.let { return it }
        target.providers.environmentVariable("PLATFORM_VERSION").orNull?.let { return it }

        val cleanCache = target.providers.gradleProperty("cleanCache").orElse("false").get().toBoolean()
        val cacheFile = target.gradle.gradleUserHomeDir.resolve("caches/personal-ops/platform-version.txt")
        val cachePath = cacheFile.toPath()

        return if (!cacheFile.exists() || cleanCache) {
            Files.createDirectories(cachePath.parent)

            val url = target.providers.gradleProperty("platformUrl")
                .orElse(target.providers.environmentVariable("PLATFORM_URL"))

            val client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build()

            val request = HttpRequest.newBuilder()
                .uri(URI.create(url.get()))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build()

            val version = client.send(request, HttpResponse.BodyHandlers.ofString()).body().trim()
            Files.writeString(cachePath, version)

            version
        } else cacheFile.readText().trim()
    }
}