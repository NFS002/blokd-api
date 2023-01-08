package io.skylines.blokd.api.plugins

import blokd.block.Block
import blokd.block.cache.Cache
import blokd.serializer.blokdObjectMapper
import io.ktor.server.application.*
import io.skylines.blokd.api.models.ServerEvent
import io.skylines.blokd.api.routing.BLOCKCHAIN_CHANNEL
import io.skylines.blokd.api.routing.CACHE_CHANNEL
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val blokdMapper = blokdObjectMapper()


fun Application.beginChannels() {
    launch {
        beginCacheChannel()
        beginBlockchainChannel()
    }
}


@OptIn(ObsoleteCoroutinesApi::class)
suspend fun beginCacheChannel() {
    var id = 0
    while (true) {
        val snapshot = blokdMapper.writeValueAsString(Cache)
        val event = ServerEvent(id = id++.toString(), data = snapshot)
        CACHE_CHANNEL.send(event)
        Cache.getOrCreate(Block())
        delay(5000)
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
suspend fun beginBlockchainChannel() {
    var id = 0
    while (true) {
        val snaphsot = blokdMapper.writeValueAsString(BLOCKCHAIN_CHANNEL)
        val event = ServerEvent(id = id++.toString(), data = snaphsot)
        BLOCKCHAIN_CHANNEL.send(event)
        delay(5000)
    }
}