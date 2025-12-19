package fivesix.personalops.gradle

import fivesix.personalops.gradle.common.asConsumable
import fivesix.personalops.gradle.versioncatalog.VersionCatalogExtension
import org.gradle.api.plugins.catalog.VersionCatalogPlugin
import org.gradle.api.attributes.Category.CATEGORY_ATTRIBUTE
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE
import org.gradle.api.attributes.Usage.VERSION_CATALOG
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    id("fivesix.personalops.gradle.maven-publish")
    `java-platform`
}

val catalog = extensions.create<VersionCatalogExtension>("catalog").apply {
    versionCatalog {
        from(layout.projectDirectory.dir("gradle").file("libs.versions.toml"))
    }
}

val exported = configurations.create(VersionCatalogPlugin.VERSION_CATALOG_ELEMENTS, asConsumable {
    description = "Artifacts for the version catalog"
    outgoing.artifact(catalog.tomlFile)
    attributes {
        attribute(CATEGORY_ATTRIBUTE, objects.named(Category.REGULAR_PLATFORM))
        attribute(USAGE_ATTRIBUTE, objects.named(VERSION_CATALOG))
    }
})

val versionCatalog = serviceOf<SoftwareComponentFactory>().adhoc("versionCatalog").apply {
    addVariantsFromConfiguration(exported) { mapToOptional() }
}

components.add(versionCatalog)

publishing {
    publications {
        create<MavenPublication>("javaPlatform") {
            from(components["javaPlatform"])
        }
    }
}