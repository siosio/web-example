package siosio.webexample.config

import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.validation.*
import org.springframework.validation.beanvalidation.*
import org.springframework.web.servlet.config.annotation.*


@Configuration
class WebConfig(private val messageSource: MessageSource) : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("login").setViewName("auth/login")
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        val localValidatorFactoryBean = LocalValidatorFactoryBean()
        localValidatorFactoryBean.setValidationMessageSource(messageSource)
        return localValidatorFactoryBean
    }

    override fun getValidator(): Validator = validator()
}
