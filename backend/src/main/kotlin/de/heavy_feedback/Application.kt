package de.heavy_feedback

import de.heavy_feedback.plugins.configureHTTP
import de.heavy_feedback.plugins.configureRouting
import de.heavy_feedback.plugins.configureSecurity
import de.heavy_feedback.plugins.configureSerialization
import de.heavy_feedback.surveyinfo.surveyInfoRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ksp.generated.module
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.ktorm.database.Database

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()

    // Use the ktor config and put it in koin to make it available to all components
    val configMap: Map<String, Any> = environment.config.toMap()
        .mapNotNull { (key, value) -> if (value != null) key to value else null }
        .toMap()

    install(Koin) {
        modules(AppModule().module)
        properties(configMap)
    }

    // Connect database without DI, connection user and pw only for development db atm
    val database = Database.connect(
        "jdbc:sqlite:heavyfeedback.sqlite"
    )

    routing {
        get("/") {
            call.respondText { "Yes it works!" }
        }

        surveyInfoRoutes()
    }
}
