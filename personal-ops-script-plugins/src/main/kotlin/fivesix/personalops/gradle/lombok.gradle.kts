package fivesix.personalops.gradle

plugins {
    java
}

afterEvaluate {
    dependencies {
        val lombokDependency = "org.projectlombok:lombok"
        compileOnly(lombokDependency)
        annotationProcessor(lombokDependency)
        testCompileOnly(lombokDependency)
        testAnnotationProcessor(lombokDependency)
    }
}