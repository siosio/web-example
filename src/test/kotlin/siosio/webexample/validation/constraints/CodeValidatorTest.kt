package siosio.webexample.validation.constraints

import org.assertj.core.api.Assertions.*
import org.junit.*
import org.junit.runner.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.junit4.*
import siosio.webexample.*
import siosio.webexample.domain.*
import javax.validation.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(WebExampleApplication::class))
class CodeValidatorTest {

    @Autowired
    private lateinit var validator: Validator

    @Test
    fun Codeに存在する文字列の場合はバリデーションOKとなること() {
        val targetBean = TargetBean()
        targetBean.code = "id1"
        assertThat(validator.validate(targetBean))
                .isEmpty()
    }

    @Test
    fun Codeに存在しない文字列の場合はバリデーションNGとなること() {
        val targetBean = TargetBean()
        targetBean.code = "invalid"
        assertThat(validator.validate(targetBean))
                .hasSize(1)
                .extracting("message")
                .containsExactly("選択してください。")
    }

    @Test
    fun 必須で未入力の場合はバリデーションNGとなること() {
        val bean = RequiredBean()
        assertThat(validator.validate(bean))
                .hasSize(1)
                .extracting("message")
                .containsExactly("選択してください。")

    }

    @Test
    fun 指定したクラスがEnumではない場合は例外() {
        class Hoge(override val id: String, override val label: String) : CodeType
        class InvalidType {
            @get:Code(type = Hoge::class)
            var invalid: String? = null;
        }

        // @formatter:off
        assertThatThrownBy {
            val invalidType = InvalidType()
            validator.validate(invalidType)
        }.isInstanceOf(ValidationException::class.java)
         .hasCauseInstanceOf(IllegalArgumentException::class.java)
        // @formatter:on
    }

    class TargetBean {
        @get:Code(type = CodeDef::class)
        var code: String? = null
    }

    class RequiredBean {
        @get:Code(type = CodeDef::class, required = true)
        var code: String? = null
    }

    enum class CodeDef(override val id: String, override val label: String) : CodeType {
        CODE1("id1", "1"),
        CODE2("id2", "2")
    }
}
