package fivesix.personalops.gradle.java

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

val DEFAULT_JAVA_VERSION = JavaVersion.VERSION_25

fun Project.setJavaDefaultVersion() {
    extensions.configure<JavaPluginExtension>("java") {
        sourceCompatibility = DEFAULT_JAVA_VERSION
        targetCompatibility = DEFAULT_JAVA_VERSION
    }
}