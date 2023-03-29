package de.heavy_feedback.database

import de.heavy_feedback.database.surveyinfo.pageElements
import de.heavy_feedback.database.surveyinfo.pages
import de.heavy_feedback.database.surveyinfo.surveys
import org.flywaydb.core.Flyway
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.ktorm.database.Database
import org.ktorm.entity.clear

@Single
class DatabaseConnector : KoinComponent {
    /**
     * Get database url either from ktor environment over koin or from koin environment for tests
     */
    private var databaseUrl: String = getKoin().getProperty<HashMap<String, String>>("database")?.get("url")
        ?: getKoin().getProperty("databaseUrl") ?: ""

    var db: Database = Database.connect(databaseUrl)

    init {
        val flyway = Flyway.configure()
            .dataSource(databaseUrl, null, null)
            .load()
        flyway.migrate()
    }

    fun reset() {
        db.useTransaction {
            db.surveys.clear()
            db.pages.clear()
            db.pageElements.clear()
        }
    }
}
