plugins {
    `kotlin-dsl`
    id("fivesix.personalops.gradle.maven-publish") version "0.0.1"
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("settings-plugin") {
            id = "fivesix.personalops.gradle.settings"
            implementationClass = "fivesix.personalops.settings.PersonalOpsSettingsPlugin"
        }
    }
}