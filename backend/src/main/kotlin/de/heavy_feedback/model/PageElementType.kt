package de.heavy_feedback.model

import com.fasterxml.jackson.annotation.JsonValue

enum class PageElementType(
    @JsonValue
    val label: String
) {
    YES_NO("yesNo"),
    OPEN("open"),
    MULTIPLE_SELECT("multipleSelect"),
    SINGLE_SELECT("singleSelect"),
    SCALA("scala"),
    RANKING("ranking"),
    RATING("rating"),
    MATRIX("matrix"),
    DOUBLE_MATRIX("doubleMatrix"),
    SLIDER("slider"),
    TEXT("text"),
    IMAGE("image"),
    LOTTERY("lottery"),
}
