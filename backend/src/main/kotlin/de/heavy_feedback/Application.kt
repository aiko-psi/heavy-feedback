package de.heavy_feedback

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import de.heavy_feedback.plugins.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        // configureSecurity()
        //configureHTTP()
        configureSerialization()
        configureRouting()

        routing {
            get("/") {
                call.respondText { "Yes it works!" }
            }
        }
    }.start(wait = true)
}
