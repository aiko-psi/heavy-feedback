package de.heavy_feedback.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PageElement(
    val id: Int,
    val type: PageElementType,
    val isReverse: Boolean,
    val isRequired: Boolean,
    val title: String,
    val note: String?,
    val tooltip: String?,
    val items: List<ScaleItem>?,
    val headers: List<String>?,
)
