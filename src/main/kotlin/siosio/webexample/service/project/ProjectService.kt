package siosio.webexample.service.project

import org.springframework.dao.*
import org.springframework.stereotype.*
import siosio.webexample.dao.project.*
import siosio.webexample.entity.*
import siosio.webexample.service.dto.*

@Service
class ProjectService(
    private val projectDao: ProjectDao,
    private val clientDao: ClientDao) {

    fun register(projectDto: ProjectDto): ProjectEntity {
        return try {
            projectDao.insert(ProjectEntity(projectDto.projectName, projectDto.projectType, projectDto.clientId))
        } catch (_: DataIntegrityViolationException) {
            throw ClientNotFoundException(projectDto.clientId)
        }.entity.apply {
            if (projectDto.hasPeriod()) {
                projectDao.insertProjectPeriod(
                    ProjectPeriodEntity(projectId, projectDto.startDate, projectDto.endDate))
            }
        }
    }

    fun searchProjects(): List<ProjectInfo> {
        return projectDao.search().map {
            val projectPeriod = projectDao.findProjectPeriod(it.projectId) 
            ProjectInfo(
                projectId = it.projectId,
                name = it.name,
                type = it.type,
                clientId = it.clientId,
                clientName = clientDao.findById(it.clientId)?.name ?: "",
                projectPeriod = projectPeriod?.let { 
                    ProjectPeriod(it.startDate, it.endDate)
                }
            )
        }
    }

    fun existsClient(clientId: Long): Boolean = clientDao.existsClient(clientId)

    class ClientNotFoundException(clientId: Long) : RuntimeException("client not found. client_id: $clientId")
}
