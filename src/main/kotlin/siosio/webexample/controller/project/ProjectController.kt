package siosio.webexample.controller.project

import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.web.bind.annotation.*
import siosio.webexample.service.project.*

@Controller
@RequestMapping("/projects")
class ProjectController(private val projectService: ProjectService) {

    @GetMapping
    fun index(model: Model): String {
        val projects = projectService.searchProjects()
        model.addAttribute("projects", projects)
        return "project/index"
    }
}
