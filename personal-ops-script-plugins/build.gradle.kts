plugins {
    `kotlin-dsl`
    `maven-publish`
}

val kotlinVersion = embeddedKotlinVersion
val springBootVersion = "3.5.6"

dependencies {
    api(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
    api("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
}

publishing {
    repositories {
        val url = providers.gradleProperty("url").getOrElse(System.getenv("MAVEN_URL"))
        val username = providers.gradleProperty("username").getOrElse(System.getenv("MAVEN_USERNAME"))
        val password = providers.gradleProperty("password").getOrElse(System.getenv("MAVEN_PASSWORD"))
        maven(url) {
            credentials {
                this.username = username
                this.password = password
            }
        }
    }
}