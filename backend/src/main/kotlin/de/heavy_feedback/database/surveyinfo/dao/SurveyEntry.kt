package de.heavy_feedback.database.surveyinfo.dao

import org.ktorm.entity.Entity

interface SurveyEntry : Entity<SurveyEntry> {
    companion object : Entity.Factory<SurveyEntry>()
    var id: Int
    var title: String?
}
