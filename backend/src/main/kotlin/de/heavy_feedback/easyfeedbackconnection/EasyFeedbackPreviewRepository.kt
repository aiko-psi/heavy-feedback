package de.heavy_feedback.easyfeedbackconnection

import de.heavy_feedback.easyfeedbackconnection.model.ContainerFromPreviewDAO
import de.heavy_feedback.easyfeedbackconnection.model.PageFromPreviewDAO
import de.heavy_feedback.easyfeedbackconnection.model.PreviewApiResponse
import de.heavy_feedback.easyfeedbackconnection.model.SurveyInfoFromPreview
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

@Single
class EasyFeedbackPreviewRepository : KoinComponent {

    private val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            jackson {
            }
        }
    }

    suspend fun getSurveyInfo(surveyUrl: String): SurveyInfoFromPreview {
        val previewUrl = convertToPreviewUrl(surveyUrl)
        val token = getApiToken(previewUrl)

        var hasNext = true
        var pageList = listOf<PageFromPreviewDAO>()
        var surveyId: Int? = null
        var surveyTitle: String? = null

        while (hasNext) {
            val container = getNextPage(token)
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

        return SurveyInfoFromPreview(
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

    private suspend fun getNextPage(token: String): ContainerFromPreviewDAO {
        val response: PreviewApiResponse = client.get(PAGE_PREVIEW_URL) {
            headers {
                append(MEMBER_TOKEN_HEADER_Name, token)
                contentType(ContentType.Application.Json)
            }
        }.body()
        return response.efContainer
    }
}
