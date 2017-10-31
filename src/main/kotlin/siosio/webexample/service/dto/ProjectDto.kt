package siosio.webexample.service.dto

import siosio.webexample.domain.*
import java.time.*

data class ProjectDto(
        val name: String,
        val type: ProjectType,
        val clientId: Long,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null
) {
    fun hasPeriod(): Boolean = startDate != null && endDate != null
}
