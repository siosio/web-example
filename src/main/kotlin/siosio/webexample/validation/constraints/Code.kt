package siosio.webexample.validation.constraints

import siosio.webexample.domain.*
import javax.validation.*
import kotlin.reflect.*

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.CLASS)
@MustBeDocumented
@Constraint(validatedBy = arrayOf(CodeValidator::class))
annotation class Code(
        val message: String = "{siosio.webexample.validation.constraints.Code.message}",
        val groups: Array<KClass<*>> = emptyArray(),
        val payload: Array<KClass<out Payload>> = emptyArray(),
        val type: KClass<out siosio.webexample.domain.CodeType>,
        val required: Boolean = false
)

private class CodeValidator : ConstraintValidator<Code, String> {

    private lateinit var type: KClass<out CodeType>
    private var required: Boolean = false

    override fun initialize(constraintAnnotation: Code) {
        type = constraintAnnotation.type
        if (!type.java.isEnum) {
            throw IllegalArgumentException("type属性には、Enumのみ設定できます。設定された、${type.qualifiedName}はEnumではありません。")
        }
        required = constraintAnnotation.required
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (required && value.isNullOrBlank()) {
            return false
        }
        return type.java.enumConstants.any {
            it.id == value
        }
    }
}
