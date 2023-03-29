package de.heavy_feedback.surveyinfo

import de.heavy_feedback.AppModule
import de.heavy_feedback.database.DatabaseConnector
import de.heavy_feedback.getSimpleTestSurvey
import de.heavy_feedback.utlis.resultLisHasError
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.fileProperties
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse

class SurveyInfoServiceTest : KoinTest {
    private val surveyInfoService: SurveyInfoService by inject()
    private val databaseConnector: DatabaseConnector by inject()

    @Before
    fun initialiseTest() {
        startKoin {
            modules(AppModule().module)
            fileProperties()
        }
    }

    @After
    fun clearDatabase() {
        databaseConnector.reset()
        stopKoin()
    }

    @Test
    fun saveSurveyToDatabase_validData_isSaved() {
        val testSurvey = getSimpleTestSurvey()
        runBlocking {
            val results = surveyInfoService.addOrUpdateSurveyInfoToDb(testSurvey)
            val hasErrors = resultLisHasError(results)
            assertFalse(hasErrors)
        }
    }
}
