package de.heavy_feedback.surveyinfo.entity

data class Page(
    val id: Int,
    val title: String?,
    val pos: Int,
    val questions: List<Question>,
)
