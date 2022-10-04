package de.heavy_feedback.easyfeedbackconnection.model

data class SurveyInfoFromPreview(
    val id: Int,
    val title: String?,
    val pages: List<PageFromPreviewDAO>,
)
