package fivesix.personalops.gradle

import fivesix.personalops.gradle.spring.DevToolsExtension

plugins {
    id("fivesix.personalops.gradle.java-app")
    id("org.springframework.boot")
}

val devTools = extensions.create<DevToolsExtension>("devTools")

afterEvaluate {
    if (devTools.devToolsEnabled.getOrElse(false)) {
        dependencies.add("developmentOnly", devTools.devToolsDependency)
    }
}