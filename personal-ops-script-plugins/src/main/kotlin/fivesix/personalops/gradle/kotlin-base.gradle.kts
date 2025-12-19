package fivesix.personalops.gradle

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("fivesix.personalops.gradle.jvm")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(provider {  JvmTarget.fromTarget(java.targetCompatibility.toString()) })
        freeCompilerArgs.addAll("-Xjvm-default=all", "-Xjsr305=strict")
    }
}