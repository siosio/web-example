package siosio.webexample.service.project

import org.springframework.stereotype.*
import siosio.webexample.dao.project.*
import siosio.webexample.entity.*
import siosio.webexample.service.dto.*

@Service
class ProjectService(
        private val projectDao: ProjectDao,
        private val clientDao: ClientDao) {

    fun register(projectDto: ProjectDto): ProjectEntity {
        return projectDao.insert(ProjectEntity(projectDto.projectName, projectDto.projectType, projectDto.clientId))
                .entity
                .let {
                    if (projectDto.hasPeriod()) {
                        projectDao.insertProjectPeriod(
                                ProjectPeriodEntity(it.projectId, projectDto.startDate, projectDto.endDate))
                    }
                    it
                }
    }

    fun existsClient(clientId: Long): Boolean {
        return clientDao.existsClient(clientId)
    }
}
