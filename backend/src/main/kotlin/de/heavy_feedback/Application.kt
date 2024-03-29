package de.heavy_feedback

import de.heavy_feedback.plugins.KoinController
import de.heavy_feedback.plugins.configureHTTP
import de.heavy_feedback.plugins.configureRouting
import de.heavy_feedback.plugins.configureSecurity
import de.heavy_feedback.plugins.configureSerialization
import de.heavy_feedback.utlis.toMapNotNull
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()

    install(Koin) {
        // If the IDE does not like the module, try running gradle kspTestKotlin
        modules(AppModule().module)

        // Use the ktor config and put it in koin to make it available to all components
        properties(environment.config.toMapNotNull())
    }

    routing {
        get("/") {
            call.respondText { "Yes it works!" }
        }

        // Register all routes from every controller
        getKoin().getAll<KoinController>().forEach { controller ->
            controller.apply { registerRoutes() }
        }
    }
}
