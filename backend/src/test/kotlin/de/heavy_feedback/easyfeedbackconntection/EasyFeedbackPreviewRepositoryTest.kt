package de.heavy_feedback.easyfeedbackconntection

import de.heavy_feedback.AppModule
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EasyFeedbackPreviewRepositoryTest : KoinTest {

    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository by inject()

    private val workingTestUrl = System.getenv("WORKING_TEST_URL")
    private val workingOriginalUrl = System.getenv("WORKING_ORIGINAL_URL")

    @Before
    fun initialiseTest() {
        startKoin {
            modules(AppModule().module)
        }
    }

    @After
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun getPreviewUrl_validUrl_returnCorrectUrl() {
        val result = easyFeedbackPreviewRepository.convertToPreviewUrl(workingOriginalUrl)
        assertEquals(workingTestUrl, result)
    }

    @Test
    fun getApiToken_validUrl_returnApiToken() {
        runBlocking {
            val result = easyFeedbackPreviewRepository.getApiToken(workingTestUrl)
            assertNotNull(result)
        }
    }

    @Test
    fun getSurveyInfo_validUrl_returnPagesWithQuestions() {
        runBlocking {
            val result = easyFeedbackPreviewRepository.getSurveyInfo(workingTestUrl)
            assertEquals(2, result.pages.size)
        }
    }
}
