package de.heavy_feedback

import TestConfig
import injectProp
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : TestConfig() {

    private val workingOriginalUrl by injectProp<String>("workingOriginalUrl")

    @Test
    fun testRoot() = testApplicationWithConfig {
        val result = client.get("/")
        assertEquals(HttpStatusCode.OK, result.status)
        assertEquals("Yes it works!", result.bodyAsText())
    }

    @Test
    fun routingToSurvey_correctURL_return200() = testApplicationWithConfig {
        client.post("/survey") {
            contentType(ContentType.Application.Json)
            setBody("{\"url\":\"$workingOriginalUrl\"}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
