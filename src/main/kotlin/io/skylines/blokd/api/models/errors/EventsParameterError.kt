package io.skylines.blokd.api.models.errors

import com.fasterxml.jackson.annotation.JsonAutoDetect
import io.ktor.http.*

val eventParameterValues = listOf("1", "true")

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class EventsParameterError {
    private val status = HttpStatusCode.BadRequest.value
    private val message = "Invalid value for events parameter. " +
            "Allowed values are $eventParameterValues"
}
