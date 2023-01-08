package io.skylines.blokd.api.routing

import io.skylines.blokd.api.models.ServerEvent
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

@OptIn(ObsoleteCoroutinesApi::class)
val CACHE_CHANNEL: BroadcastChannel<ServerEvent> = BroadcastChannel(10)

@OptIn(ObsoleteCoroutinesApi::class)
val BLOCKCHAIN_CHANNEL: BroadcastChannel<ServerEvent> = BroadcastChannel(10)

val viewableKafkaProperties: List<String> = listOf(
    "bootstrap.servers",
)

