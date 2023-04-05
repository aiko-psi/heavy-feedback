package de.heavy_feedback.easyfeedbackconntection

import TestConfig
import de.heavy_feedback.easyfeedbackconnection.EasyFeedbackPreviewRepository
import injectProp
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Assume
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * This class of tests needs an internet connection because it tests the retrieval of data from the real easyfeedback api.
 * If there is no internet connection, most of the tests are skipped.
 */
class EasyFeedbackPreviewRepositoryTest : TestConfig() {

    private val easyFeedbackPreviewRepository: EasyFeedbackPreviewRepository by inject()

    private val workingTestUrl by injectProp<String>("workingTestUrl")
    private val workingOriginalUrl by injectProp<String>("workingOriginalUrl")

    private suspend fun testInternet(): Boolean {
        val client = HttpClient(Apache)
        return try {
            val testCallResult = client.get("https://www.google.de/")
            testCallResult.status == HttpStatusCode.OK
        } catch (e: Throwable) {
            false
        }
    }

    @Test
    fun getPreviewUrl_validUrl_returnCorrectUrl() = testApplicationWithConfig {
        val result = easyFeedbackPreviewRepository.convertToPreviewUrl(workingOriginalUrl)
        assertEquals(workingTestUrl, result)
    }

    @Test
    fun getApiToken_validUrl_returnApiToken() = testApplicationWithConfig {
        runBlocking {
            // Only run this test if there is internet
            Assume.assumeTrue(testInternet())

            val result = easyFeedbackPreviewRepository.getApiToken(workingTestUrl)
            assertNull(result)
        }
    }
    @Test
    fun getSurveyInfo_validUrl_returnPagesWithQuestions() = testApplicationWithConfig {
        runBlocking {
            // Only run this test if there is internet
            Assume.assumeTrue(testInternet())

            val result = easyFeedbackPreviewRepository.getSurveyInfo(workingTestUrl)
            // Test questionnaire has eight pages at the moment
            assertEquals(8, result.pages.size)
        }
    }
}
