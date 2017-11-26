package siosio.webexample.validation

import javax.validation.*
import javax.validation.constraints.*
import kotlin.reflect.*

@NotNull(message = "{siosio.webexample.validation.ClientId.NotNull.message}")
@Digits(integer = 18, fraction = 0)
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.CLASS)
@Constraint(validatedBy = emptyArray())
annotation class ClientId(
        val message: String = "{message.ClientId}",
        val groups: Array<KClass<*>> = emptyArray(),
        val payload: Array<KClass<out Payload>> = emptyArray()
)
