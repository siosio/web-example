package siosio.webexample.controller.project

import org.junit.*
import org.junit.runner.*
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.mock.mockito.*
import org.springframework.security.test.context.support.*
import org.springframework.security.test.web.servlet.request.*
import org.springframework.test.context.junit4.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import siosio.webexample.domain.*
import siosio.webexample.entity.*
import siosio.webexample.service.dto.*
import siosio.webexample.service.project.*
import java.time.*

@RunWith(SpringRunner::class)
@WebMvcTest(NewProjectController::class)
class NewProjectControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var mockService: ProjectService

    @Test
    @WithMockUser
    fun バリデーションOKの場合確認画面に遷移すること() {
        `when`(mockService.existsClient(1234554321L))
                .thenReturn(true)

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?confirm=")
                        .param("projectName", "プロジェクト名")
                        .param("projectType", ProjectType.NEW_PROJECT.name)
                        .param("clientId", "1234554321")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(status().isOk)
                .andExpect(view().name("project/new_confirm"))
                .andExpect(model().hasNoErrors<Any>())
        //@formatter:on
    }

    @Test
    @WithMockUser
    fun バリデーションエラーの場合は入力画面に戻ること() {

        fun ModelResultMatchers.hasFieldErrorCodeInForm(fieldName: String, errorCode: String): ResultMatcher {
            return attributeHasFieldErrorCode("newProjectForm", fieldName, errorCode)
        }

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?confirm=")
                        .param("projectName", "")
                        .param("projectType", "invalid")
                        .param("clientId", "aaaa")
                        .param("startDate", "2017/11/1")
                        .param("endDate", "2017/11/31")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(view().name("project/new"))
                .andExpect(model().hasFieldErrorCodeInForm("projectName", "NotEmpty"))
                .andExpect(model().hasFieldErrorCodeInForm("projectType", "Code"))
                .andExpect(model().hasFieldErrorCodeInForm("clientId", "typeMismatch"))
                .andExpect(model().hasFieldErrorCodeInForm("startDate", "typeMismatch"))
                .andExpect(model().hasFieldErrorCodeInForm("endDate", "typeMismatch"))
        //@formatter:on
    }

    @Test
    @WithMockUser
    fun 顧客IDが顧客テーブルに存在しない場合はエラーなので入力画面に戻ること() {
        given(mockService.existsClient(999L))
                .willReturn(false)

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?confirm=")
                        .param("projectName", "プロジェクト")
                        .param("projectType", ProjectType.MAINTENANCE_PROJECT.name)
                        .param("clientId", "999")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(view().name("project/new"))
                .andExpect(model().attributeHasFieldErrors("newProjectForm", "clientId"))
        //@formatter:on
    }

    @Test
    @WithMockUser
    fun プロジェクト期間が一方しか入力されていない場合エラーとなり入力画面に戻ること() {
        given(mockService.existsClient(123L))
                .willReturn(true)

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?confirm=")
                        .param("projectName", "プロジェクト")
                        .param("projectType", ProjectType.NEW_PROJECT.name)
                        .param("clientId", "123")
                        .param("endDate", "2017/11/30")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(view().name("project/new"))
                .andExpect(model().attributeHasFieldErrors("newProjectForm", "projectPeriod"))
        //@formatter:on
    }

    @Test
    @WithMockUser
    fun プロジェクトが登録できること() {
        given(mockService.register(
                ProjectDto("プロジェクト", ProjectType.MAINTENANCE_PROJECT, 999, LocalDate.of(2017, 11, 1), LocalDate.of(2017, 11, 30))))
                .willReturn(ProjectEntity(9999L, "プロジェクト", ProjectType.MAINTENANCE_PROJECT, 999))

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?complete=")
                        .param("projectName", "プロジェクト")
                        .param("projectType", ProjectType.MAINTENANCE_PROJECT.name)
                        .param("clientId", "999")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(view().name("redirect:/projects"))
        //@formatter:on
    }

    @Test
    @WithMockUser
    fun 登録時にプロジェクトが存在しない場合入力画面に戻ること() {
        given(mockService.register(
                ProjectDto("プロジェクト", ProjectType.MAINTENANCE_PROJECT, 999, LocalDate.of(2017, 11, 1), LocalDate.of(2017, 11, 30))))
                .willThrow(ProjectService.ClientNotFoundException(999))

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?complete=")
                        .param("projectName", "プロジェクト")
                        .param("projectType", ProjectType.MAINTENANCE_PROJECT.name)
                        .param("clientId", "999")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                 )
                .andExpect(view().name("project/new"))
                .andExpect(model().attributeHasFieldErrors("newProjectForm", "clientId"))
        //@formatter:on
    }
}
