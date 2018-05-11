package siosio.webexample.config

import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import org.springframework.security.web.csrf.*
import siosio.webexample.service.auth.*

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userService: UserService

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/webjars/**", "/css/**", "/js/**").permitAll()
                .antMatchers("/projects/**").hasRole("USER")
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("id")
                        .defaultSuccessUrl("/projects", true)
                        .permitAll()
                .and()
                    .logout()
                        .invalidateHttpSession(true)
                .and()
                    .csrf()
                        .csrfTokenRepository(CookieCsrfTokenRepository())
        // @formatter:on
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}
