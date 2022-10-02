package de.heavy_feedback.easyfeedbackconnection.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PreviewApiResponse(
    val efContainer: ContainerFromPreviewDAO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContainerFromPreviewDAO(
    val process: ProcessInfoFromPreviewDAO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProcessInfoFromPreviewDAO(
    val page: PageFromPreviewDAO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PageFromPreviewDAO(
    val id: Int,
    val title: String?,
    val pos: Int,
    val isLastPage: Boolean,
    val elements: List<PageElement>?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PageElement(
    val id: Int,
    val type: String,
    val isReverse: Boolean?,
    val isRequired: Boolean?,
    val title: String?,
    val note: String?,
    val tooltip: String?,
    val items: List<ScaleItemDAO>?,
    val headers: List<String>?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ScaleItemDAO(
    val id: Int,
    val title: String,
    val type: String,
)
