package de.heavy_feedback.database

import org.koin.core.annotation.Single
import org.ktorm.database.Database

@Single
class DatabaseConnector {
    var db: Database = Database.connect(
        "jdbc:sqlite:heavyfeedback.sqlite"
    )
}
