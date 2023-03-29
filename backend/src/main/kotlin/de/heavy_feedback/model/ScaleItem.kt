package de.heavy_feedback.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ScaleItem(
    val id: Int,
    val title: String,
    val type: ScaleItemType,
)
