package siosio.webexample.config

import org.springframework.context.annotation.*
import org.springframework.web.servlet.config.annotation.*

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("login").setViewName("auth/login")
    }
}
