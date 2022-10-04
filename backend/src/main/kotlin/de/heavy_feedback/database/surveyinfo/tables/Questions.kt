package de.heavy_feedback.database.surveyinfo.tables

import de.heavy_feedback.database.surveyinfo.dao.QuestionEntry
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.text

object Questions : Table<QuestionEntry>("QUESTIONS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val type = text("TYPE").bindTo { it.type }
    val isRequired = boolean("IS_REQUIRED").bindTo { it.isRequired }
    val isReverse = boolean("IS_REVERSE").bindTo { it.isReverse }
    val title = text("TITLE").bindTo { it.title }
    val note = text("NOTE").bindTo { it.note }
    val tooltip = text("TOOLTIP").bindTo { it.tooltip }
}
