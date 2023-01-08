package io.skylines.blokd.api.plugins

import blokd.serializer.configureObjectMapper
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            configureObjectMapper(this)
        }
    }
}
