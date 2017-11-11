package siosio.webexample.domain

enum class ProjectType(override val label: String) : CodeType {
    NEW_PROJECT("新規プロジェクト"),
    MAINTENANCE_PROJECT("保守プロジェクト")
}
