package de.heavy_feedback.model

import com.fasterxml.jackson.annotation.JsonValue

enum class ScaleItemType(
    @JsonValue
    val label: String
) {
    RADIO("radio"),
    MATRIX_ITEM("matrixItem"),
    CHECKBOX("checkbox"),
    INPUT("input"),
    OTHER(""),
}
