package de.heavy_feedback.surveyinfo

import de.heavy_feedback.database.surveyinfo.SurveyInfoRepository
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import org.koin.core.annotation.Single

@Single
class SurveyInfoService(
    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository,
    private val surveyInfoRepository: SurveyInfoRepository
) {

    suspend fun fetchSurveyInfoAndAddToDb(url: String): Int {
        val rawInfo = easyFeedbackPreviewRepository.getSurveyInfo(url)

        surveyInfoRepository.addSurvey(rawInfo.id, rawInfo.title)
        rawInfo.pages.forEach { page ->
            surveyInfoRepository.addPage(page.id, page.title, page.pos, rawInfo.id)
            page.elements?.forEach { element ->
                // TODO filter out questions, add scales
                surveyInfoRepository.addQuestion(
                    element.id,
                    element.type,
                    element.isRequired,
                    element.isReverse,
                    element.title,
                    element.note,
                    element.tooltip,
                    page.id
                )
            }
        }
        return rawInfo.id
    }
}
