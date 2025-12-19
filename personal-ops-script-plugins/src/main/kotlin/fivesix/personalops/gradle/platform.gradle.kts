package fivesix.personalops.gradle

import fivesix.personalops.gradle.common.DEFAULT_SPRING_CLOUD_VERSION
import fivesix.personalops.gradle.common.DEFAULT_SPRING_VERSION
import fivesix.personalops.gradle.common.KOTLIN_VERSION
import fivesix.personalops.gradle.common.asBucket

val platforms by configurations.creating(asBucket {
    withDependencies {
        addAll(
            listOf(
                project.dependencies.platform("org.springframework.boot:spring-boot-dependencies:$DEFAULT_SPRING_VERSION"),
                project.dependencies.platform("org.springframework.cloud:spring-cloud-dependencies:$DEFAULT_SPRING_CLOUD_VERSION"),
                project.dependencies.platform("org.jetbrains.kotlin:kotlin-bom:$KOTLIN_VERSION")
            )
        )
    }
})