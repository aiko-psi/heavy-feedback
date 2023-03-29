package de.heavy_feedback.surveyinfo

import de.heavy_feedback.plugins.KoinController
import de.heavy_feedback.surveyinfo.dto.SurveyUrlDto
import de.heavy_feedback.utlis.resultLisHasError
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.locations.Location
import io.ktor.server.locations.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.koin.core.component.inject

var logger = KotlinLogging.logger {}

@Single
class SurveyInfoController : KoinController() {
    private val surveyInfoService: SurveyInfoService by inject()

    override fun Route.registerRoutes() {
        post<Routes.Survey> {
            val surveyInfoData = call.receive<SurveyUrlDto>()

            val results = surveyInfoService.refreshSurveyInfoFromEasyFeedback(surveyInfoData.url)
            if (resultLisHasError(results)) {
                call.respond(HttpStatusCode.BadRequest, results)
            } else {
                call.respond(results)
            }
        }
    }

    object Routes {
        @Location("/survey")
        class Survey
    }
}
