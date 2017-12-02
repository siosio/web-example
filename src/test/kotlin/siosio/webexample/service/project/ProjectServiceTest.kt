package siosio.webexample.service.project

import org.assertj.core.api.Assertions.*
import org.junit.*
import org.junit.runner.*
import org.seasar.doma.boot.autoconfigure.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.*
import org.springframework.test.context.junit4.*
import siosio.webexample.*
import siosio.webexample.domain.*
import siosio.webexample.helper.*
import siosio.webexample.service.dto.*
import java.time.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(WebExampleApplication::class))
@TestPropertySource("/test.properties")
class ProjectServiceTest {

    @Autowired
    private lateinit var sut: ProjectService

    @Autowired
    private lateinit var domaConfig: DomaConfig

    @Before
    fun setUp() {
        domaConfig.dataSource.connection.use {
            it.createStatement().use {
                it.execute("set referential_integrity false")
                it.execute("truncate table PROJECT")
                it.execute("truncate table CLIENT")
                it.execute("set referential_integrity true")
                it.execute("insert into CLIENT (CLIENT_ID, NAME) values (100, 'name')")
                it.execute("insert into CLIENT (CLIENT_ID, NAME) values (101, 'name_101')")
                it.execute("insert into CLIENT (CLIENT_ID, NAME) values (102, 'name_102')")
                it.execute("insert into CLIENT (CLIENT_ID, NAME) values (103, 'name_103')")
                it.execute("insert into CLIENT (CLIENT_ID, NAME) values (500, 'name')")
            }
        }
    }

    @Test
    fun プロジェクトが登録できること() {
        val result = sut.register(
            ProjectDto(
                "テストプロジェクト",
                ProjectType.NEW_PROJECT,
                100,
                LocalDate.of(2016, Month.APRIL, 1),
                LocalDate.of(2017, Month.DECEMBER, 31)
            )
        )

        // assert project
        assertThat(searchProject(domaConfig))
            .hasSize(1)
            .extracting("project_id", "name", "type", "client_id")
            .containsExactly(tuple(result.projectId, result.name, result.type.toString(), result.clientId))

        // assert project period
        assertThat(searchProjectPeriod(domaConfig))
            .hasSize(1)
            .extracting("project_id", "start_date", "end_date")
            .containsExactly(tuple(result.projectId, "2016-04-01", "2017-12-31"))
    }

    @Test
    fun プロジェクト期間が未指定の場合はプロジェクトのみ登録されること() {
        val result = sut.register(
            ProjectDto(
                "テストプロジェクト",
                ProjectType.MAINTENANCE_PROJECT,
                100
            )
        )

        assertThat(searchProject(domaConfig))
            .extracting("project_id", "name", "type", "client_id")
            .containsExactly(tuple(result.projectId, result.name, result.type.toString(), result.clientId))
        // assert project period
        assertThat(searchProjectPeriod(domaConfig))
            .`as`("プロジェクト期間は未指定なので登録されない")
            .isEmpty()
    }

    @Test
    fun 顧客の存在チェック() {
        assertThat(sut.existsClient(500))
            .`as`("存在しているのでtrue")
            .isTrue()
        assertThat(sut.existsClient(501))
            .`as`("存在していないのでfalse")
            .isFalse()
    }

    @Test
    fun プロジェクトが検索できること() {
        // setup
        domaConfig.dataSource.connection.use {
            it.createStatement().use {
                it.execute("set referential_integrity false")
                it.execute("truncate table PROJECT")
                it.execute("set referential_integrity true")
            }
            it.prepareStatement("insert into PROJECT (NAME, TYPE, CLIENT_ID) values (?, ?, ?)").use { ps ->
                (1..3).forEach {
                    ps.setString(1, "name_$it")
                    ps.setString(2, when (it % 2) {
                        0 -> "NEW_PROJECT"
                        else -> "MAINTENANCE_PROJECT"
                    })
                    ps.setInt(3, 100 + it)
                    ps.addBatch()
                }
                ps.executeBatch()
            }
        }

        val result = sut.searchProjects()
        assertThat(result)
            .extracting("name", "type", "clientId", "clientName")
            .containsExactly(
                tuple("name_1", ProjectType.MAINTENANCE_PROJECT, 101L, "name_101"),
                tuple("name_2", ProjectType.NEW_PROJECT, 102L, "name_102"),
                tuple("name_3", ProjectType.MAINTENANCE_PROJECT, 103L, "name_103")
            )
    }
}

