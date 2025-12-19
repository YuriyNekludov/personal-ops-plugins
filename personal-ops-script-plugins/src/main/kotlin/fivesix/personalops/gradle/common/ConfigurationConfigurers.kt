package fivesix.personalops.gradle.common

import org.gradle.api.artifacts.Configuration

inline fun asBucket(crossinline action: Configuration.() -> Unit = {}): Configuration.() -> Unit = {
    isCanBeConsumed = false
    isCanBeResolved = false
    action()
}

inline fun asConsumable(crossinline action: Configuration.() -> Unit = {}): Configuration.() -> Unit = {
    isCanBeConsumed = true
    isCanBeResolved = false
    action()
}