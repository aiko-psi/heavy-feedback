package de.heavy_feedback.surveyinfo

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

    val surveyInfoService: SurveyInfoService by inject()

    post<Routes.Survey> {
        val surveyInfoData = call.receive<SurveyUrlDto>()

        // TODO Just for testing! Do not give this back :-)
        val id = surveyInfoService.fetchSurveyInfoAndAddToDb(surveyInfoData.url)
        call.respond(id)
    }
}

object Routes {
    @Location("/survey")
    class Survey
}
