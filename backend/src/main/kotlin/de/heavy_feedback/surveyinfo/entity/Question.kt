package de.heavy_feedback.surveyinfo.entity

data class Question(
    val id: Int,
    val type: String, // TODO Change to Enum
    val isRequired: Boolean,
    val isReverse: Boolean,
    val title: String,
    val note: String?,
    val tooltip: String?,
)
