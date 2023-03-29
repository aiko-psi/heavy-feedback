package de.heavy_feedback.model

data class Survey(
    val id: Int,
    val title: String?,
    val pages: List<Page>,
)
