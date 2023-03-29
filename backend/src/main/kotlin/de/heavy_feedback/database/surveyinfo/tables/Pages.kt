package de.heavy_feedback.database.surveyinfo.tables

import de.heavy_feedback.database.surveyinfo.dao.PageEntry
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object Pages : Table<PageEntry>("PAGES") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val title = text("TITLE").bindTo { it.title }
    val pos = int("POS").bindTo { it.pos }
    val surveyId = int("SURVEY_ID").references(Surveys) { it.survey }
}
