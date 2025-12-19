package fivesix.personalops.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.time.Duration

class PersonalOpsSettingsPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        val platformConfigurer = target.extensions.create<PersonalOpsPlatformExt>("platformConfigurer")
        val platformGroup = "fivesix.personalops"
        val platformArtifact = "personal-ops-platform"
        val platformVersion = resolvePlatformVersion(target, platformConfigurer)

        target.dependencyResolutionManagement {
            versionCatalogs {
                create("libs") { from("$platformGroup:$platformArtifact:$platformVersion") }
            }
        }
    }

    private fun resolvePlatformVersion(target: Settings, platformConfigurer: PersonalOpsPlatformExt): String {
        target.providers.gradleProperty("platformVersion").orNull?.let { return it }
        target.providers.environmentVariable("PLATFORM_VERSION").orNull?.let { return it }

        val cacheRefresh = platformConfigurer.cleanCache.getOrElse(false)
        val cacheFile = target.gradle.gradleUserHomeDir.resolve("caches/personal-ops/platform-version.txt")
        val cachePath = cacheFile.toPath()

        return if (!cacheFile.exists() || cacheRefresh) {
            Files.createDirectories(cachePath.parent)

            val url = target.providers.gradleProperty("platformUrl")
                .orElse(target.providers.environmentVariable("PLATFORM_URL"))

            val client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build()

            val request = HttpRequest.newBuilder()
                .uri(URI(url.get()))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build()

            val version = client.send(request, HttpResponse.BodyHandlers.ofString()).body().trim()
            Files.writeString(cachePath, version)

            version
        } else cacheFile.readText().trim()
    }
}