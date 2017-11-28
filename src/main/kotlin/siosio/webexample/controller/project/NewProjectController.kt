package siosio.webexample.controller.project

import org.slf4j.*
import org.springframework.format.annotation.*
import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.*
import siosio.webexample.domain.*
import siosio.webexample.entity.*
import siosio.webexample.service.dto.*
import siosio.webexample.service.project.*
import siosio.webexample.validation.*
import siosio.webexample.validation.constraints.*
import java.time.*
import javax.validation.constraints.*

@RequestMapping("/projects/create")
@Controller
class NewProjectController(
        private val projectService: ProjectService) {

    private val logger = LoggerFactory.getLogger(NewProjectController::class.java)

    @ModelAttribute
    fun setupForm(): NewProjectForm {
        return NewProjectForm()
    }

    @GetMapping()
    fun newProject(model: Model): String {
        return "project/new"
    }

    @PostMapping(params = arrayOf("back"))
    fun back(form: NewProjectForm): String {
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
    fun complete(form: NewProjectForm, bindingResult: BindingResult, redirectAttributes: RedirectAttributes): String {
        val projectDto = ProjectDto(
                projectName = form.projectName!!,
                projectType = ProjectType.valueOf(form.projectType!!),
                clientId = form.clientId!!,
                startDate = form.startDate,
                endDate = form.endDate
        )
        val entity: ProjectEntity
        try {
            entity = projectService.register(projectDto)
        } catch (e: ProjectService.ClientNotFoundException) {
            bindingResult.addError(FieldError("newProjectForm", "clientId", "顧客が存在しません。"))
            return "project/new"
        }

        if (logger.isDebugEnabled) {
            logger.debug("プロジェクトを登録: ${entity.projectId}")
        }
        redirectAttributes.addFlashAttribute("message", "${entity.name}を登録しました。")
        return "redirect:/projects"
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

        @AssertTrue(message = "プロジェクト期間を入力する場合は両方入力してください。")
        fun isProjectPeriod(): Boolean {
            return (startDate == null && endDate == null)
                    || (startDate != null && endDate != null)
        }

        fun getProjectTypeName(): String = projectType?.let { ProjectType.valueOf(it).label } ?: ""

        var clientName: String? = null
    }
}

