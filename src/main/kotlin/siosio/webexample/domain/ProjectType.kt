package siosio.webexample.domain

enum class ProjectType(override val id: String, override val label: String) : CodeType {
    NEW_PROJECT("new", "新規プロジェクト"),
    MAINTENANCE_PROJECT("maintenance", "保守プロジェクト")
}
