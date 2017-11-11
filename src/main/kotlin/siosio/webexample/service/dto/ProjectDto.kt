package siosio.webexample.service.dto

import siosio.webexample.domain.*
import java.time.*

data class ProjectDto(
        val projectName: String,
        val projectType: ProjectType,
        val clientId: Long,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null
) {
    fun hasPeriod(): Boolean = startDate != null && endDate != null
}
