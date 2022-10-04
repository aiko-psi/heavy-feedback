package de.heavy_feedback.database.surveyinfo.tables

import de.heavy_feedback.database.surveyinfo.dao.SurveyEntry
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object Surveys : Table<SurveyEntry>("SURVEYS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val title = text("TITLE").bindTo { it.title }
}
