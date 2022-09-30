package de.heavy_feedback

import de.heavy_feedback.plugins.configureHTTP
import de.heavy_feedback.plugins.configureRouting
import de.heavy_feedback.plugins.configureSecurity
import de.heavy_feedback.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.database.Database

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()

    // Connect database without DI, connection user and pw only for development docker db atm
    val database = Database.connect(
        "jdbc:postgresql://localhost:5435/postgres",
        user = "heavyuser",
        password = "secretOnly4Dev3lopment"
    )

    routing {
        get("/") {
            call.respondText { "Yes it works!" }
        }
    }

}
