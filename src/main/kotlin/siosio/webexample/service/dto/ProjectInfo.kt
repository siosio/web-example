package siosio.webexample.service.dto

import siosio.webexample.domain.*
import siosio.webexample.entity.*
import java.time.*

data class ProjectInfo(
    val projectId: Long,
    val name: String,
    val type: ProjectType,
    val clientId: Long,
    val clientName: String,
    val projectPeriod: ProjectPeriod?
)

data class ProjectPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate
)
