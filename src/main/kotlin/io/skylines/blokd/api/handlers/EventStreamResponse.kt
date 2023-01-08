package io.skylines.blokd.api.handlers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.skylines.blokd.api.models.ServerEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.withContext

suspend fun ApplicationCall.eventStream(events: ReceiveChannel<ServerEvent>) {
    response.cacheControl(CacheControl.NoCache(null))
    respondTextWriter(contentType = ContentType.Text.EventStream) {
        withContext(Dispatchers.IO) {
            events.consumeEach {
                write("id: ${it.id}\n")
                it.data.lines().forEach {
                    write("data: $it\n")
                }
                write("\n")
                flush()
            }
        }
    }
}