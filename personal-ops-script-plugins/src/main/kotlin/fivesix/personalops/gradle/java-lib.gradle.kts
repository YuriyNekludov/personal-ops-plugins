package fivesix.personalops.gradle

import org.gradle.kotlin.dsl.get

plugins {
    `java-library`
    id("fivesix.personalops.gradle.maven-publish")
    id("fivesix.personalops.gradle.java-app")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("interviewTracker") {
                from(components["java"])
                versionMapping {
                    allVariants {
                        fromResolutionResult()
                    }
                }
            }
        }
    }
}