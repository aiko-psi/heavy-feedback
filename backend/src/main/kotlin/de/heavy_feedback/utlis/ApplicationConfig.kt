package de.heavy_feedback.utlis

import io.ktor.server.config.ApplicationConfig

fun ApplicationConfig.toMapNotNull() = this.toMap()
    .mapNotNull { (key, value) -> if (value != null) key to value else null }
    .toMap()
