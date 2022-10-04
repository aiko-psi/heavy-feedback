package de.heavy_feedback.database.surveyinfo.dao

import org.ktorm.entity.Entity

interface PageEntry : Entity<PageEntry> {
    companion object : Entity.Factory<PageEntry>()
    var id: Int
    var title: String?
    var pos: Int
    var survey: SurveyEntry
}
