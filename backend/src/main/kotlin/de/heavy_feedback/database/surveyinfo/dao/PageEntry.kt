package de.heavy_feedback.database.surveyinfo.dao

import de.heavy_feedback.model.Page
import org.ktorm.entity.Entity

interface PageEntry : Entity<PageEntry> {
    var id: Int
    var title: String?
    var pos: Int
    var survey: SurveyEntry

    companion object : Entity.Factory<PageEntry>() {
        fun fromPage(pageDAO: Page, surveyEntry: SurveyEntry): PageEntry = PageEntry {
            id = pageDAO.id
            title = pageDAO.title
            pos = pageDAO.pos
            survey = surveyEntry
        }
    }
}
