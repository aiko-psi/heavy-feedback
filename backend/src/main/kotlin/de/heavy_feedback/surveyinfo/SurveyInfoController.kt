package de.heavy_feedback.surveyinfo

import de.heavy_feedback.database.DatabaseConnector
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import de.heavy_feedback.surveyinfo.dto.SurveyUrlDto
import io.ktor.server.application.call
import io.ktor.server.locations.Location
import io.ktor.server.locations.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mu.KotlinLogging
import org.koin.ktor.ext.inject

var logger = KotlinLogging.logger {}

fun Route.surveyInfoRoutes() {

    val surveyRepoPreview: EasyFeedbackPreviewRepository by inject()
    val databaseConnector: DatabaseConnector by inject()

    post<Routes.Info.Survey> {
        val surveyInfoData = call.receive<SurveyUrlDto>()

        // TODO Just for testing
        val name = databaseConnector.db.name
        logger.debug { "Name database: $name" }

        // TODO Just for testing! Do not give this back :-)
        val result = surveyRepoPreview.getSurveyInfo(surveyInfoData.url)
        call.respond(result)
    }
}

object Routes {
    @Location("/info")
    class Info {

        @Location("/survey/{id}")
        data class Survey(val id: Int)
    }
}
