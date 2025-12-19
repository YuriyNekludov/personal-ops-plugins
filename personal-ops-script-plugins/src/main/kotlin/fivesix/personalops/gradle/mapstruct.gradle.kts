package fivesix.personalops.gradle

plugins {
    java
}

val mapstructProcessorDependencies = listOf(
    "org.projectlombok:lombok-mapstruct-binding",
    "org.mapstruct:mapstruct-processor"
)
val mapstructDependency = "org.mapstruct:mapstruct"
val mapstructDefaultComponentModel = "mapstruct.defaultComponentModel"

dependencies {
    implementation(mapstructDependency)
    mapstructProcessorDependencies.forEach {
        annotationProcessor(it)
        testAnnotationProcessor(it)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-A$mapstructDefaultComponentModel=spring")
}