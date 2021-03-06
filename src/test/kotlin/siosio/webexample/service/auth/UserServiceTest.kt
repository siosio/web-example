package siosio.webexample.service.auth

import com.github.springtestdbunit.*
import com.github.springtestdbunit.annotation.*
import com.github.springtestdbunit.bean.*
import org.assertj.core.api.Assertions.*
import org.junit.*
import org.junit.runner.*
import org.seasar.doma.boot.autoconfigure.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.context.annotation.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.password.*
import org.springframework.test.context.*
import org.springframework.test.context.junit4.*
import org.springframework.test.context.support.*
import org.springframework.test.context.transaction.*
import siosio.webexample.*
import siosio.webexample.dao.user.*
import siosio.webexample.helper.*
import javax.sql.*


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(WebExampleApplication::class, UserServiceTest.Config::class))
@TestPropertySource("classpath:test.properties")
@TestExecutionListeners(value = [
    (DependencyInjectionTestExecutionListener::class),
    (DirtiesContextTestExecutionListener::class),
    (TransactionalTestExecutionListener::class),
    (DbUnitTestExecutionListener::class)]
)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader::class, databaseConnection = ["connection"])
class UserServiceTest {

    @Autowired
    private lateinit var sut: UserService

    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var domaConfig: DomaConfig

    @Before
    fun setUp() {
        domaConfig.dataSource.connection.use {
            it.autoCommit = false
            it.createStatement().use {
                it.execute("truncate table user_roles")
                it.execute("truncate table users cascade ")
            }
            it.prepareStatement("insert into users values (?, ?)").use {
                it.setString(1, "test_user")
                it.setString(2, passwordEncoder.encode("password"))
                it.executeUpdate()
            }
            it.prepareStatement("insert into user_roles (user_id, role) values (?, ?)").use {
                it.setString(1, "test_user")
                it.setString(2, "ROLE_USER")
                it.executeUpdate()
            }
            it.commit()
        }
    }

    @Test
    fun ユーザ情報が取得できること() {
        val userDetails = sut.loadUserByUsername("test_user")
        assertThat(userDetails.username)
                .isEqualTo("test_user")

        assertThat(userDetails.authorities)
                .hasSize(1)
                .contains(SimpleGrantedAuthority("ROLE_USER"))

        assertThat(userDetails.password)
                .matches { passwordEncoder.matches("password", it) }

    }

    @Test
    fun ユーザ情報が取得できない場合はUsernameNotFoundException() {
        assertThatThrownBy { sut.loadUserByUsername("not_found") }
                .isInstanceOf(UsernameNotFoundException::class.java)
    }

    @Configuration
    class Config {
        @Bean
        fun connection(dataSource: DataSource): DatabaseDataSourceConnectionFactoryBean {
            val databaseDataSourceConnectionFactoryBean = DatabaseDataSourceConnectionFactoryBean(dataSource)
            databaseDataSourceConnectionFactoryBean.setSchema("test")
            return databaseDataSourceConnectionFactoryBean
        }
    }

}
