package de.heavy_feedback

import de.heavy_feedback.model.Page
import de.heavy_feedback.model.PageElement
import de.heavy_feedback.model.PageElementType
import de.heavy_feedback.model.ScaleItem
import de.heavy_feedback.model.ScaleItemType
import de.heavy_feedback.model.Survey

fun getSimpleTestSurvey() = Survey(
    id = 123,
    title = "Test Questionnaire",
    pages = listOf(
        Page(
            id = 203,
            title = "Introduction",
            pos = 1,
            isLastPage = true,
            elements = listOf(
                PageElement(
                    id = 304,
                    type = PageElementType.SINGLE_SELECT,
                    isReverse = false,
                    isRequired = true,
                    title = "This is a single Question",
                    note = null,
                    tooltip = "You have to answer it.",
                    items = listOf(
                        ScaleItem(
                            id = 4011,
                            title = "Option 1",
                            type = ScaleItemType.CHECKBOX
                        )
                    ),
                    headers = listOf("Header One")
                )
            )
        )
    )

)
