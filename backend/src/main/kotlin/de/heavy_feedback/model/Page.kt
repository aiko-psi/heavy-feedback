package de.heavy_feedback.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Page(
    val id: Int,
    val title: String?,
    val pos: Int,
    val isLastPage: Boolean,
    val elements: List<PageElement>?,
)
