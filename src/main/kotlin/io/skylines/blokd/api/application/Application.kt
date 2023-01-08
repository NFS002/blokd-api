package io.skylines.blokd.api.application

import blokd.block.cache.Cache
import blokd.serializer.blokdObjectMapper
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.skylines.blokd.api.plugins.beginChannels
import io.skylines.blokd.api.plugins.configureSecurity
import io.skylines.blokd.api.plugins.configureSerialization
import io.skylines.blokd.api.plugins.configureTemplating
import io.skylines.blokd.api.routing.configureRouting
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureTemplating()
    configureSerialization()
    configureRouting()
    beginChannels()
}
