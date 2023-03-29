package de.heavy_feedback.easyfeedbackconntection

import de.heavy_feedback.AppModule
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assume
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

/**
 * This class of tests needs an internet connection because it tests the retrieval of data from the real easyfeedback api.
 * If there is no internet connection, most of the tests are skipped.
 */
class EasyFeedbackPreviewRepositoryTest : KoinTest {

    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository by inject()

    private var workingTestUrl: String? = null
    private var workingOriginalUrl: String? = null

    private suspend fun testInternet(): Boolean {
        val client = HttpClient(Apache)
        return try {
            val testCallResult = client.get("https://www.google.de/")
            testCallResult.status == HttpStatusCode.OK
        } catch (e: Throwable) {
            false
        }
    }

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
            // Only run this test if there is internet
            Assume.assumeTrue(testInternet())

            val result = easyFeedbackPreviewRepository.getApiToken(workingTestUrl!!)
            assertNotNull(result)
        }
    }

    @Test
    fun getSurveyInfo_validUrl_returnPagesWithQuestions() {
        assertNotNull(workingOriginalUrl)
        runBlocking {
            // Only run this test if there is internet
            Assume.assumeTrue(testInternet())

            val result = easyFeedbackPreviewRepository.getSurveyInfo(workingTestUrl!!)
            // Test questionnaire has two pages at the moment
            assertEquals(8, result.pages.size)
        }
    }
}
