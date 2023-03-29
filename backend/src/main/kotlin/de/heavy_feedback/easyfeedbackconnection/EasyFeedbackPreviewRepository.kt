package de.heavy_feedback.easyfeedbackconnection

import de.heavy_feedback.easyfeedbackconnection.dao.ContainerFromPreviewDAO
import de.heavy_feedback.easyfeedbackconnection.dao.PreviewApiResponse
import de.heavy_feedback.model.Page
import de.heavy_feedback.model.Survey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

private val logger = KotlinLogging.logger {}

const val PREVIEW_IDENTIFIER = "vorschau"
const val PAGE_PREVIEW_URL = "https://app.easy-feedback.com/api/pages/next"
const val MEMBER_TOKEN_HEADER_Name = "X-Api-Member-Token"

/**
 * Repository to get survey information from easyfeedback through their preview api.
 * Easyfeedback only has an api to get the question results. To get the question texts and option texts, we request
 * every page of the questionnaire over the preview api. It is possible to decode the information from there.
 */
@Single
class EasyFeedbackPreviewRepository : KoinComponent {

    private val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            jackson {
            }
        }
    }

    suspend fun getSurveyInfo(surveyUrl: String): Survey {
        // To use the preview api, the url has to be transformed and an api token is needed.
        val previewUrl = convertToPreviewUrl(surveyUrl)
        val token = getApiToken(previewUrl)

        var hasNext = true
        var pageList = listOf<Page>()
        var surveyId: Int? = null
        var surveyTitle: String? = null

        // Get all pages from the preview questionnaire
        while (hasNext) {
            val container = getNextPageInfo(token)
            val page = container.process.page
            pageList = pageList.plus(page)
            hasNext = !page.isLastPage

            surveyId = surveyId ?: container.survey.id
            surveyTitle = surveyTitle ?: container.survey.title
        }
        logger.debug { "${pageList.size} pages successfully found." }
        if (surveyId == null) {
            throw error("SurveyId not found")
        }

        return Survey(
            id = surveyId,
            title = surveyTitle,
            pages = pageList
        )
    }

    fun convertToPreviewUrl(surveyUrl: String): String {
        val sep = "/"
        val partedUrl = surveyUrl.split(sep)
        val address = partedUrl.subList(0, 3).joinToString(sep)
        val idWithPrefix = partedUrl.subList(4, partedUrl.size).joinToString(sep)
        return "$address/$PREVIEW_IDENTIFIER/$idWithPrefix"
    }

    suspend fun getApiToken(previewUrl: String): String {
        val response: HttpResponse = client.get(previewUrl)
        val content = response.bodyAsText()
        val regex = "apiMemberToken\":\\s*\"([^\"]*)".toRegex()
        return regex.find(content)?.groupValues?.get(1)
            ?: throw error("Token not fount in content.")
    }

    /**
     * Get next page information from preview api and parse it to container object
     */
    private suspend fun getNextPageInfo(token: String): ContainerFromPreviewDAO {
        val response: PreviewApiResponse = client.get(PAGE_PREVIEW_URL) {
            headers {
                append(MEMBER_TOKEN_HEADER_Name, token)
                contentType(ContentType.Application.Json)
            }
        }.body()
        return response.efContainer
    }
}
