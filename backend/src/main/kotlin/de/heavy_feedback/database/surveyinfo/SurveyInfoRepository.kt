package de.heavy_feedback.database.surveyinfo

import de.heavy_feedback.database.DatabaseConnector
import de.heavy_feedback.database.surveyinfo.dao.PageEntry
import de.heavy_feedback.database.surveyinfo.dao.QuestionEntry
import de.heavy_feedback.database.surveyinfo.dao.SurveyEntry
import de.heavy_feedback.database.surveyinfo.tables.Pages
import de.heavy_feedback.database.surveyinfo.tables.Questions
import de.heavy_feedback.database.surveyinfo.tables.Surveys
import org.koin.core.annotation.Single
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf

val Database.surveys get() = this.sequenceOf(Surveys)
val Database.pages get() = this.sequenceOf(Pages)
val Database.questions get() = this.sequenceOf(Questions)

@Single
class SurveyInfoRepository(private val databaseConnector: DatabaseConnector) {

    fun addSurvey(idToAdd: Int, titleToAdd: String?) = databaseConnector.db.surveys.add(
        SurveyEntry {
            id = idToAdd
            title = titleToAdd
        }
    )

    fun getSurveyById(surveyIdToFind: Int) = databaseConnector.db.surveys.find { it.id eq surveyIdToFind }

    fun addPage(idToAdd: Int, titleToAdd: String?, posToAdd: Int, surveyId: Int) =
        databaseConnector.db.pages.add(
            PageEntry {
                id = idToAdd
                title = titleToAdd
                pos = posToAdd
                survey = databaseConnector.db.surveys.find { it.id eq surveyId }!!
            }
        )

    fun getPageById(pageIdToFind: Int) = databaseConnector.db.pages.find { it.id eq pageIdToFind }

    fun addQuestion(
        idToAdd: Int,
        typeToAdd: String,
        isRequiredToAdd: Boolean,
        isReverseToAdd: Boolean,
        titleToAdd: String,
        noteToAdd: String?,
        tooltipToAdd: String?,
        pageId: Int
    ) = databaseConnector.db.questions.add(
        QuestionEntry {
            id = idToAdd
            type = typeToAdd
            isRequired = isRequiredToAdd
            isReverse = isReverseToAdd
            title = titleToAdd
            note = noteToAdd
            tooltip = tooltipToAdd
            page = databaseConnector.db.pages.find { it.id eq pageId }!!
        }
    )
}
