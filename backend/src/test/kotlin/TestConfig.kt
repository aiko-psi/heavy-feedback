
import de.heavy_feedback.database.DatabaseConnector
import de.heavy_feedback.utlis.toMapNotNull
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

open class TestConfig : KoinTest {

    private val appConfig = ApplicationConfig("application-custom-test.conf")

    fun testApplicationWithConfig(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = appConfig
        }

        // Force application to start first because ktor testing lazy starts it otherwise and we need koin now
        startApplication()
        val runningKoin = getKoin()

        // Feed ktor config to koin to get it everywhere
        appConfig.toMapNotNull().map {
            runningKoin.setProperty(it.key, it.value)
        }
        // Put test specific keys in koin
        runningKoin.setProperty("workingTestUrl", "https://easy-feedback.com/vorschau/1545691/vnmA5H")
        runningKoin.setProperty("workingOriginalUrl", "https://easy-feedback.com/survey/1545691/vnmA5H")

        block()
        val database: DatabaseConnector by inject()
        database.reset()
        stopKoin()
    }
}
