package de.heavy_feedback.surveyinfo

import de.heavy_feedback.database.surveyinfo.SurveyInfoRepository
import de.heavy_feedback.database.surveyinfo.dao.SurveyEntry
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import de.heavy_feedback.model.Page
import de.heavy_feedback.model.PageElement
import de.heavy_feedback.model.Survey
import de.heavy_feedback.utlis.Action
import de.heavy_feedback.utlis.ActionResult
import de.heavy_feedback.utlis.ResultElement
import de.heavy_feedback.utlis.evalResult
import org.koin.core.annotation.Single

@Single
class SurveyInfoService(
    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository,
    private val surveyInfoRepository: SurveyInfoRepository
) {

    /**
     * Fetch information from easyfeedback and refresh them in the database. If the data is not already there it will be added.
     */
    suspend fun refreshSurveyInfoFromEasyFeedback(url: String): List<ActionResult> =
        addOrUpdateSurveyInfoToDb(fetchSurveyFromEasyFeedback(url))

    private suspend fun fetchSurveyFromEasyFeedback(url: String): Survey =
        easyFeedbackPreviewRepository.getSurveyInfo(url)

    fun addOrUpdateSurveyInfoToDb(surveyInfo: Survey): List<ActionResult> {
        var results = listOf<ActionResult>()

        results = results.plus(addOrUpdateSurvey(surveyInfo.id, surveyInfo.title))

        surveyInfo.pages.forEach { page ->
            results = results.plus(addOrUpdatePage(page, surveyInfo.id))
            page.elements?.forEach { element ->
                results = results.plus(addOrUpdatePageElement(element, page.id))
            }
        }
        return results
    }

    private fun addOrUpdateSurvey(idToAdd: Int, titleToAdd: String?): ActionResult {
        val entry = SurveyEntry {
            id = idToAdd
            title = titleToAdd
        }

        return if (surveyInfoRepository.getSurveyById(idToAdd) != null) {
            evalResult(ResultElement.PAGE, Action.UPDATE) { surveyInfoRepository.updateSurvey(entry) }
        } else {
            evalResult(ResultElement.PAGE, Action.CREATE) { surveyInfoRepository.addSurvey(entry) }
        }
    }

    private fun addOrUpdatePage(pageToAdd: Page, surveyId: Int): ActionResult {
        val pageFromDb = surveyInfoRepository.getPageById(pageToAdd.id)

        return if (pageFromDb != null) {
            evalResult(ResultElement.PAGE, Action.UPDATE) {
                surveyInfoRepository.updatePage(
                    pageToAdd,
                    pageFromDb.survey
                )
            }
        } else {
            evalResult(ResultElement.PAGE, Action.CREATE) { surveyInfoRepository.addPage(pageToAdd, surveyId) }
        }
    }

    private fun addOrUpdatePageElement(elementToAdd: PageElement, pageId: Int): ActionResult {
        val elementFromDb = surveyInfoRepository.getPageElementById(elementToAdd.id)

        return if (elementFromDb != null) {
            evalResult(ResultElement.PAGE_ELEMENT, Action.UPDATE) {
                surveyInfoRepository.updatePageElement(
                    elementToAdd,
                    pageId
                )
            }
        } else {
            evalResult(ResultElement.PAGE_ELEMENT, Action.CREATE) {
                surveyInfoRepository.addPageElement(
                    elementToAdd,
                    pageId
                )
            }
        }
    }
}
