package de.heavy_feedback.database.surveyinfo

import de.heavy_feedback.database.DatabaseConnector
import de.heavy_feedback.database.surveyinfo.dao.PageElementEntry
import de.heavy_feedback.database.surveyinfo.dao.PageEntry
import de.heavy_feedback.database.surveyinfo.dao.SurveyEntry
import de.heavy_feedback.database.surveyinfo.tables.PageElements
import de.heavy_feedback.database.surveyinfo.tables.Pages
import de.heavy_feedback.database.surveyinfo.tables.Surveys
import de.heavy_feedback.model.Page
import de.heavy_feedback.model.PageElement
import org.koin.core.annotation.Single
import org.ktorm.database.Database
import org.ktorm.database.Transaction
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.update

val Database.surveys get() = this.sequenceOf(Surveys)
val Database.pages get() = this.sequenceOf(Pages)
val Database.pageElements get() = this.sequenceOf(PageElements)

@Single
class SurveyInfoRepository(private val databaseConnector: DatabaseConnector) {

    fun <T> useTransaction(func: (Transaction) -> T) = databaseConnector.db.useTransaction { func }

    fun addSurvey(entry: SurveyEntry) = databaseConnector.db.surveys.add(entry)

    fun updateSurvey(entry: SurveyEntry) = databaseConnector.db.surveys.update(entry)

    fun getSurveyById(surveyIdToFind: Int) = databaseConnector.db.surveys.find { it.id eq surveyIdToFind }

    fun addPage(page: Page, surveyId: Int) = databaseConnector.db.useTransaction {
        val survey = databaseConnector.db.surveys.find { it.id eq surveyId } ?: throw Error("Survey not found.")
        databaseConnector.db.pages.add(PageEntry.fromPage(page, survey))
    }

    fun updatePage(page: Page, survey: SurveyEntry) =
        databaseConnector.db.pages.update(PageEntry.fromPage(page, survey))

    fun getPageById(pageIdToFind: Int) = databaseConnector.db.pages.find { it.id eq pageIdToFind }

    fun addPageElement(pageElementToAdd: PageElement, pageId: Int) = databaseConnector.db.useTransaction {
        val page = databaseConnector.db.pages.find { it.id eq pageId } ?: throw Error("Page not found.")
        databaseConnector.db.pageElements.add(PageElementEntry.fromPageElement(pageElementToAdd, page))
    }

    fun updatePageElement(pageElementToAdd: PageElement, pageId: Int) = databaseConnector.db.useTransaction {
        val page = databaseConnector.db.pages.find { it.id eq pageId } ?: throw Error("Page not found.")
        databaseConnector.db.pageElements.update(PageElementEntry.fromPageElement(pageElementToAdd, page))
    }

    fun getPageElementById(elementIdToFind: Int) = databaseConnector.db.pageElements.find { it.id eq elementIdToFind }
}
