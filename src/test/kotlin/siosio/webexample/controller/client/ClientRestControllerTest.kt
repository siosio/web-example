package siosio.webexample.controller.client

import org.hamcrest.Matchers.*
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import siosio.webexample.entity.*
import siosio.webexample.service.client.*

@RunWith(SpringRunner::class)
@WebMvcTest(ClientRestController::class)
class ClientRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var mockService: ClientService

    @Test
    @WithMockUser
    fun 顧客情報が取得できること() {
        given(mockService.searchClients())
                .willReturn(listOf(
                        ClientEntity(1L, "顧客1"),
                        ClientEntity(999L, "顧客999")
                ))

        mockMvc.perform(get("/clients/search"))
                .andExpect(status().isOk)
                .andExpect(header().string("content-type", containsString("application/json")))
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[0].clientId").value(1))
                .andExpect(jsonPath("$[0].name").value("顧客1"))
                .andExpect(jsonPath("$[1].clientId").value(999))
                .andExpect(jsonPath("$[1].name").value("顧客999"))
    }
}
