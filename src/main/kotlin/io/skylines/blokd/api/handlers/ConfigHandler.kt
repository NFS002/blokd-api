package io.skylines.blokd.api.handlers

import blokd.extensions.BASE_PROPERTIES
import blokd.extensions.CLIENT_PROPERTIES
import blokd.extensions.loadKafkaConfig
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.skylines.blokd.api.models.Keys
import io.skylines.blokd.api.routing.viewableKafkaProperties
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.li
import kotlinx.html.ul

object ConfigHandler {

    suspend fun GET_config_blokd(call: ApplicationCall) {
        call.respond(BASE_PROPERTIES)
    }

    suspend fun GET_config_client(call: ApplicationCall) {
        call.respond(CLIENT_PROPERTIES)
    }

    suspend fun GET_config_keys(call: ApplicationCall) {
        call.respond(Keys)
    }

    suspend fun GET_config_kafka(call: ApplicationCall) {
        val kafkaConfig = loadKafkaConfig()
        val filteredKafkaConfig = kafkaConfig.filter {entry ->
            viewableKafkaProperties.contains(entry.key.toString())
        }
        call.respond(filteredKafkaConfig)
    }

    suspend fun GET_config(call: ApplicationCall) {
        call.respondHtml {
            body {
                ul {
                    li {
                        a(href = "/configs/blokd") {
                            +"blokd"
                        }
                    }
                    li {
                        a(href = "/configs/client") {
                            +"client"
                        }
                    }
                    li {
                        a(href = "/configs/keys") {
                            +"keys"
                        }
                    }
                    li {
                        a(href = "/configs/kafka.config") {
                            +"kafka.config"
                        }
                    }
                }
            }
        }
    }
}