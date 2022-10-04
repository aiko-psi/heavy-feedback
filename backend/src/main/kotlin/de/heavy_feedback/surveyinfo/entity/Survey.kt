package de.heavy_feedback.surveyinfo.entity

data class Survey(
    val id: Int,
    val title: String,
    val pages: List<Page>,
)
