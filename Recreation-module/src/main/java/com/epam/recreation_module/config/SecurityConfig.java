package com.epam.recreation_module.config;

import com.epam.recreation_module.security.JwtFilter;
import com.epam.recreation_module.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final AuthServiceImpl authServiceImpl;
    private final JwtFilter jwtFilter;

    public SecurityConfig(@Lazy AuthServiceImpl authServiceImpl, @Lazy JwtFilter jwtFilter) {
        this.authServiceImpl = authServiceImpl;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(
                        "/authorization/login",
                        "/authorization/authToken",
                        "/commentary/deleteMy/**", "/commentary/editMy/**", "/commentary/add", "/commentary/all",
                        "/event/allNotDelete", "/event/get/**", "/event/confirmEvent",
                        "/order/**",
                        "/photo/get/**",
                        "/user/register",
                        "/recreation/*",
                        "/ticket/getUnsoldTickets/*",
                        "/v2/api-docs",             // swagger
                        "/webjars/",                // swagger-ui webjars
                        "/swagger-resources/**",    // swagger-ui resources
                        "/swagger-ui/**",    // swagger-ui resources
                        "/configuration/",         // swagger configuration
                        "/*.html", "/favicon.ico", "//*.html", "//*.css", "/**//*js"
                ).permitAll()
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .permitAll();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }
}
/*"/cityManagement/**",  "/recreation/**", "/event/**", "/authorization/**", "/order/**", "/ticket/**").permitAll()
"/api/v1/auth/login",
"/v2/api-docs", // swagger
"/",
"/webjars/", // swagger-ui webjars
"/swagger-resources/", // swagger-ui resources
"/configuration/", // swagger configuration
").permitAll()*/


