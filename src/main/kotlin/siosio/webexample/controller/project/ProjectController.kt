package siosio.webexample.controller.project

import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/projects")
class ProjectController {

    @GetMapping
    fun index(): String {
        return "project/index"
    }
}