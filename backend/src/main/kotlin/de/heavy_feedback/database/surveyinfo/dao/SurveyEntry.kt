package de.heavy_feedback.database.surveyinfo.dao

import de.heavy_feedback.easyfeedbackconnection.dao.SurveyInfoFromPreviewDAO
import org.ktorm.entity.Entity

interface SurveyEntry : Entity<SurveyEntry> {
    var id: Int
    var title: String?

    companion object : Entity.Factory<SurveyEntry>() {
        fun fromSurveyDAO(surveyDAO: SurveyInfoFromPreviewDAO): SurveyEntry =
            SurveyEntry {
                id = surveyDAO.id
                title = surveyDAO.title
            }
    }
}
