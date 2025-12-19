package fivesix.personalops.gradle

import fivesix.personalops.gradle.java.setJavaDefaultVersion

plugins {
    java
    id("fivesix.personalops.gradle.platform")
}

setJavaDefaultVersion()

sourceSets.configureEach {
    configurations {
        val platforms by this
        named(compileClasspathConfigurationName) { extendsFrom(platforms) }
        named(runtimeClasspathConfigurationName) { extendsFrom(platforms) }
        named(annotationProcessorConfigurationName) { extendsFrom(platforms) }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-parameters")
    }
    withType<Test>().configureEach {
        jvmArgs("-XX:+EnableDynamicAgentLoading")
    }
    test {
        useJUnitPlatform()
    }
}