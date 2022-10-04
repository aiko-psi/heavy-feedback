package de.heavy_feedback.database

import org.flywaydb.core.Flyway
import org.koin.core.annotation.Single
import org.ktorm.database.Database

const val databaseUrl = "jdbc:sqlite:heavyfeedback.sqlite"

@Single
class DatabaseConnector {
    var db: Database = Database.connect(
        databaseUrl
    )

    init {
        val flyway = Flyway.configure()
            .dataSource(databaseUrl, null, null)
            .load()
        flyway.migrate()
    }
}
