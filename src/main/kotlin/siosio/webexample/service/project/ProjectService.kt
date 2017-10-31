package siosio.webexample.service.project

import org.springframework.stereotype.*
import siosio.webexample.dao.project.*
import siosio.webexample.entity.*
import siosio.webexample.service.dto.*

@Service
class ProjectService(private val projectDao: ProjectDao) {

    fun register(projectDto: ProjectDto): ProjectEntity {
        return projectDao.insert(
                ProjectEntity(projectDto.name, projectDto.type, projectDto.clientId)).entity.let {
            if (projectDto.hasPeriod()) {
                projectDao.insertProjectPeriod(
                        ProjectPeriodEntity(it.projectId, projectDto.startDate, projectDto.endDate))
            }
            it
        }
    }
}
