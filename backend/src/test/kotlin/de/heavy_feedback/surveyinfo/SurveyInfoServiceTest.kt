package de.heavy_feedback.surveyinfo

import TestConfig
import de.heavy_feedback.getSimpleTestSurvey
import de.heavy_feedback.utlis.resultListHasError
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertFalse

class SurveyInfoServiceTest : TestConfig() {
    private val surveyInfoService: SurveyInfoService by inject()

    @Test
    fun saveSurveyToDatabase_validData_isSaved() = testApplicationWithConfig {
        val testSurvey = getSimpleTestSurvey()
        runBlocking {
            val results = surveyInfoService.addOrUpdateSurveyInfoToDb(testSurvey)
            val hasErrors = resultListHasError(results)
            assertFalse(hasErrors)
        }
    }
}
