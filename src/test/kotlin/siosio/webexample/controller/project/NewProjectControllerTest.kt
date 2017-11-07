package siosio.webexample.controller.project

import org.junit.*
import org.junit.runner.*
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.mock.mockito.*
import org.springframework.security.test.context.support.*
import org.springframework.test.context.junit4.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import siosio.webexample.service.project.*

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
        given(mockService.existsClient(1234554321L))
                .willReturn(true)

        //@formatter:off
        mockMvc.perform(
                post("/projects/create?confirm=")
                        .param("projectName", "プロジェクト名")
                        .param("projectType", "new")
                        .param("clientId", "1234554321")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
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
                        .param("projectType", "maintenance")
                        .param("clientId", "999")
                        .param("startDate", "2017/11/01")
                        .param("endDate", "2017/11/30")
                 )
                .andExpect(view().name("project/new"))
                .andExpect(model().attributeHasFieldErrors("newProjectForm", "clientId"))
        //@formatter:on
    }
}
