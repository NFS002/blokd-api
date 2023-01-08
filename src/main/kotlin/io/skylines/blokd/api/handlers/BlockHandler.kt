package io.skylines.blokd.api.handlers

import blokd.block.BlockChain
import blokd.block.cache.Cache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.skylines.blokd.api.models.errors.EventsParameterError
import io.skylines.blokd.api.models.errors.eventParameterValues
import io.skylines.blokd.api.routing.BLOCKCHAIN_CHANNEL
import io.skylines.blokd.api.routing.CACHE_CHANNEL
import kotlinx.coroutines.ObsoleteCoroutinesApi


object BlockHandler {

    suspend fun GET_blocks_cached(call: ApplicationCall) {
        call.respond(Cache)
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    suspend fun `GET_blocks_cached&events`(call: ApplicationCall, events:String) {
        when (eventParameterValues.contains(events)) {
            true -> call.eventStream(CACHE_CHANNEL.openSubscription())
            else -> call.respond(status = HttpStatusCode.BadRequest, message = EventsParameterError())
        }
    }

    suspend fun GET_blocks_chained(call: ApplicationCall) {
        call.respond(BlockChain)
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    suspend fun `GET_blocks_chained&events`(call: ApplicationCall, events: String) {
        when (eventParameterValues.contains(events)) {
            true -> call.eventStream(BLOCKCHAIN_CHANNEL.openSubscription())
            else -> call.respond(status = HttpStatusCode.BadRequest, message = EventsParameterError())
        }
    }
}