package siosio.webexample.controller.project

import org.springframework.format.annotation.*
import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*
import siosio.webexample.domain.*
import siosio.webexample.service.project.*
import siosio.webexample.validation.*
import siosio.webexample.validation.constraints.*
import java.time.*

@RequestMapping("/projects/create")
@Controller
class NewProjectController(private val projectService: ProjectService) {

    @ModelAttribute
    fun setupForm(): NewProjectForm {
        return NewProjectForm()
    }

    @GetMapping()
    fun newProject(model: Model): String {
        return "project/new"
    }

    @PostMapping(params = arrayOf("confirm"))
    fun confirm(@Validated form: NewProjectForm, bindingResult: BindingResult): String {
        form.clientId?.let {
            if (!projectService.existsClient(it)) {
                bindingResult.addError(FieldError("newProjectForm", "clientId", "顧客が存在しません。"))
            }
        }

        return when {
            bindingResult.hasErrors() -> "project/new"
            else -> "project/new_confirm"
        }
    }

    @PostMapping(params = arrayOf("complete"))
    fun complete(form: NewProjectForm): String {
        println("form = ${form.projectName}")
        return "project/new"
    }

    class NewProjectForm {
        @get:ProjectName
        var projectName: String? = null

        @get:Code(type = ProjectType::class)
        var projectType: String? = null

        @get:ClientId
        @get:NumberFormat(pattern = "#")
        var clientId: Long? = null

        @get:DateTimeFormat(pattern = "yyyy/MM/dd")
        var startDate: LocalDate? = null

        @get:DateTimeFormat(pattern = "yyyy/MM/dd")
        var endDate: LocalDate? = null

        fun getProjectTypeName(): String {
            return ProjectType.values().firstOrNull() {
                it.id == projectType
            }?.label ?: ""
        }
    }
}

