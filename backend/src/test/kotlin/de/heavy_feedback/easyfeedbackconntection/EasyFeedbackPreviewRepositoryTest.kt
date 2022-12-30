package de.heavy_feedback.easyfeedbackconntection

import de.heavy_feedback.AppModule
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.fileProperties
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EasyFeedbackPreviewRepositoryTest : KoinTest {

    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository by inject()

    private var workingTestUrl: String? = null
    private var workingOriginalUrl: String? = null

    @Before
    fun initialiseTest() {
        startKoin {
            modules(AppModule().module)
            fileProperties()
        }
        workingTestUrl = getKoin().getProperty("workingTestUrl")
        workingOriginalUrl = getKoin().getProperty("workingOriginalUrl")
    }

    @After
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun getPreviewUrl_validUrl_returnCorrectUrl() {
        assertNotNull(workingOriginalUrl)
        val result = easyFeedbackPreviewRepository.convertToPreviewUrl(workingOriginalUrl!!)
        assertEquals(workingTestUrl, result)
    }

    @Test
    fun getApiToken_validUrl_returnApiToken() {
        assertNotNull(workingOriginalUrl)
        runBlocking {
            val result = easyFeedbackPreviewRepository.getApiToken(workingTestUrl!!)
            assertNotNull(result)
        }
    }

    @Test
    fun getSurveyInfo_validUrl_returnPagesWithQuestions() {
        assertNotNull(workingOriginalUrl)
        runBlocking {
            val result = easyFeedbackPreviewRepository.getSurveyInfo(workingTestUrl!!)
            assertEquals(2, result.pages.size)
        }
    }
}
