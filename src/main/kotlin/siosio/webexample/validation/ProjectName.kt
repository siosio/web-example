package siosio.webexample.validation

import org.hibernate.validator.constraints.*
import javax.validation.*
import javax.validation.constraints.*
import javax.validation.constraints.NotEmpty
import kotlin.reflect.*

@NotNull
@NotEmpty
@Length(max = 64, message = "{message.Length.max}")
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.CLASS)
@Constraint(validatedBy = emptyArray())
annotation class ProjectName(
        val message: String = "{message.ProjectName}",
        val groups: Array<KClass<*>> = emptyArray(),
        val payload: Array<KClass<out Payload>> = emptyArray()
)

