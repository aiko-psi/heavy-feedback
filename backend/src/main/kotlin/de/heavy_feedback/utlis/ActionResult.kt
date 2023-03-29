package de.heavy_feedback.utlis

data class ActionResult(val element: ResultElement, val action: Action, val result: Result<Int>)

enum class ResultElement {
    SURVEY, PAGE, PAGE_ELEMENT,
}

enum class Action {
    CREATE, READ, UPDATE, DELETE, RETRIEVE,
}

fun evalResult(element: ResultElement, action: Action, func: () -> Int): ActionResult {
    return try {
        val id = func()
        ActionResult(element, action, Result.success(id))
    } catch (e: Throwable) {
        ActionResult(element, action, Result.failure(e))
    }
}

fun resultLisHasError(results: List<ActionResult>): Boolean = results.map { it.result }.any { it.isFailure }
