package io.skylines.blokd.api.routing

import blokd.block.cache.Cache
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.skylines.blokd.api.handlers.BlockHandler.GET_blocks_cached
import io.skylines.blokd.api.handlers.BlockHandler.`GET_blocks_cached&events`
import io.skylines.blokd.api.handlers.BlockHandler.GET_blocks_chained
import io.skylines.blokd.api.handlers.BlockHandler.`GET_blocks_chained&events`
import io.skylines.blokd.api.handlers.ConfigHandler.GET_config
import io.skylines.blokd.api.handlers.ConfigHandler.GET_config_blokd
import io.skylines.blokd.api.handlers.ConfigHandler.GET_config_client
import io.skylines.blokd.api.handlers.ConfigHandler.GET_config_keys
import io.skylines.blokd.api.handlers.ConfigHandler.GET_config_kafka
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

suspend fun ApplicationCall.respondCache(events: ReceiveChannel<Cache>) {
    response.cacheControl(CacheControl.NoCache(null))
    respondTextWriter(contentType = ContentType.Text.EventStream) {
        for (event in events) {
            withContext(Dispatchers.IO) {
                val jsonCache = jacksonObjectMapper().writeValueAsString(event.blocks)
                write("this: ${jsonCache}")
                flush()
            }
        }
    }
}

fun Application.configureRouting() {

    routing {
        getAllRoutes().forEach {

        }

        get {
            call.respondText("Hello World!")
        }

        route("configs") {
            get {
                GET_config(call)
            }
            get("blokd") {
                GET_config_blokd(call)
            }

            get("client") {
                GET_config_client(call)
            }

            get("keys") {
                GET_config_keys(call)
            }

            get("kafka.config") {
                GET_config_kafka(call)
            }
        }

        route("blocks") {
            post {

            }

            get("cached") {
                val events = call.request.queryParameters["events"]
                when (events == null) {
                    true -> GET_blocks_cached(call)
                    false -> `GET_blocks_cached&events`(call, events)
                }
            }

            get("chained") {
                val events = call.request.queryParameters["events"]
                when (events == null) {
                    true -> GET_blocks_chained(call)
                    false -> `GET_blocks_chained&events`(call, events)
                }
            }
        }

        get("test") {
            call.respondText(
                """
                        <html>
                            <head></head>
                            <body>
                                <script type="text/javascript">
                                    var source = new EventSource('/blocks/cached?events=true');
                                   
                                    source.addEventListener('message', function(e) {
                                        document.open()
                                        document.write(e.data);
                                        document.close()
                                    }, false);

                                    source.addEventListener('open', function(e) {
                                        console.log(e);
                                    }, false);

                                    source.addEventListener('error', function(e) {
                                        if (e.readyState == EventSource.CLOSED) {
                                            console.warn(e);
                                        } else {
                                            console.error(e);
                                        }
                                    }, false);
                                </script>
                            </body>
                        </html>
                    """.trimIndent(),
                contentType = ContentType.Text.Html
            )
        }
    }
}
