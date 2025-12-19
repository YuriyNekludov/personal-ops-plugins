package fivesix.personalops.settings

import org.gradle.api.provider.Property

abstract class PersonalOpsPlatformExt {

    abstract val cleanCache: Property<Boolean>

    fun configure(configurer: PersonalOpsPlatformProperties.() -> Unit) {
        val props = PersonalOpsPlatformProperties().apply(configurer)
        configure(props)
    }

    private fun configure(props: PersonalOpsPlatformProperties) {
        cleanCache.set(props.cleanCache)
    }

    init {
        cleanCache.convention(false)
    }

    class PersonalOpsPlatformProperties {
        var cleanCache: Boolean = false
    }
}