package siosio.webexample.config

import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import siosio.webexample.service.auth.*

@Configuration
class SecurityConfig(private val userService: UserService) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/webjars/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("id")
                .defaultSuccessUrl("/projects")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun userDetailsService(): UserDetailsService = userService


}