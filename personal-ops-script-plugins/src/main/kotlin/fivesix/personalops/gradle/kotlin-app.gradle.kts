package fivesix.personalops.gradle

plugins {
    id("fivesix.personalops.gradle.kotlin-base")
    kotlin("kapt")
}

configurations {
    val platforms by this
    this.kapt { extendsFrom(platforms) }
    this.kaptTest { extendsFrom(platforms) }
}

kapt {
    includeCompileClasspath = false
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}