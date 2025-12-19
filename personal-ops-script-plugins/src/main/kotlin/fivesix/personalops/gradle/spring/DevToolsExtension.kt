package fivesix.personalops.gradle.spring

import org.gradle.api.provider.Property

abstract class DevToolsExtension {
    abstract val devToolsEnabled: Property<Boolean>

    val devToolsDependency = "org.springframework.boot:spring-boot-devtools"

    fun enableDevTools(selector: () -> Boolean) {
        devToolsEnabled.set(selector())
    }

    init {
        devToolsEnabled.convention(false)
    }
}