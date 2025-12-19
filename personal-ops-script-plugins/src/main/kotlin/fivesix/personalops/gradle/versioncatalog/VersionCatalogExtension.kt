package fivesix.personalops.gradle.versioncatalog

import org.gradle.api.Action
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.file.FileOperations
import javax.inject.Inject

abstract class VersionCatalogExtension {
    abstract val tomlFile: RegularFileProperty

    @get:Inject
    protected abstract val fileOperations: FileOperations

    fun versionCatalog(configure: Action<VersionCatalogBuilder>) {
        configure.execute(VersionCatalogBuilder())
    }

    inner class VersionCatalogBuilder {
        fun from(file: Any) {
            tomlFile.set(fileOperations.file(file))
        }
    }
}