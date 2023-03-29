package de.heavy_feedback.database.surveyinfo.dao

import de.heavy_feedback.model.PageElement
import de.heavy_feedback.model.PageElementType
import org.ktorm.entity.Entity

interface PageElementEntry : Entity<PageElementEntry> {
    var id: Int
    var type: PageElementType
    var isRequired: Boolean
    var isReverse: Boolean
    var title: String
    var note: String?
    var tooltip: String?
    var page: PageEntry

    companion object : Entity.Factory<PageElementEntry>() {
        fun fromPageElement(element: PageElement, pageEntry: PageEntry) = PageElementEntry {
            id = element.id
            type = element.type
            isRequired = element.isRequired
            isReverse = element.isReverse
            title = element.title
            note = element.note
            tooltip = element.tooltip
            page = pageEntry
        }
    }
}
