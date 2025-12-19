package fivesix.personalops.gradle

import fivesix.personalops.gradle.maven.gitHubRepository

plugins {
    `maven-publish`
}

publishing {
    repositories {
        gitHubRepository(project)
    }
}