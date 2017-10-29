package siosio.webexample.controller.project

import org.springframework.web.bind.annotation.*

@RequestMapping("/projects")
class NewProjectController {

    @GetMapping("/new")
    fun newProject(): String {
        return "project/new"
    }
}
