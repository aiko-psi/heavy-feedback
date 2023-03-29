package de.heavy_feedback.easyfeedbackconnection.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.heavy_feedback.model.Page

/**
 * Objects for the easyfeedback api to use with jackson to decode from response.
 * Only define the props we really need.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class PreviewApiResponse(
    val efContainer: ContainerFromPreviewDAO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContainerFromPreviewDAO(
    val process: ProcessInfoFromPreviewDAO,
    val survey: SurveyInfoFromPreviewDAO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SurveyInfoFromPreviewDAO(
    val id: Int,
    val title: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProcessInfoFromPreviewDAO(
    val page: Page,
)
