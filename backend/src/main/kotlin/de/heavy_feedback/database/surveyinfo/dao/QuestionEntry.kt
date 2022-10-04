package de.heavy_feedback.database.surveyinfo.dao

import org.ktorm.entity.Entity

interface QuestionEntry : Entity<QuestionEntry> {
    companion object : Entity.Factory<QuestionEntry>()
    var id: Int
    var type: String
    var isRequired: Boolean
    var isReverse: Boolean
    var title: String
    var note: String?
    var tooltip: String?
    var page: PageEntry
}
