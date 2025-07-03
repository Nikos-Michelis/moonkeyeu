package com.moonkeyeu.core.api.configuration.security;

import com.moonkeyeu.core.api.configuration.security.jwt.JwtAuthenticationFilter;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import com.moonkeyeu.core.api.settings.FilterChainExceptionHandler;
import com.moonkeyeu.core.api.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.moonkeyeu.core.api.user.model.Permissions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private final LogoutHandler logoutHandler;
    private final AuthenticationProvider authenticationProvider;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final CookieServiceProvider cookieServiceProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf
                .csrfTokenRepository(cookieServiceProvider.buildCsrfCookie())
                .ignoringRequestMatchers(
                        "/public/**", "/csrf/**", "/actuator/**", "/crawler/**"
                )
        );
        http.cors(withDefaults())
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "/csrf/token",
                                        "/public/**",
                                        "/auth/**",
                                        "/oauth2/**",
                                        "/images/**",
                                        "/community/**",
                                        "/actuator/prometheus",
                                        "/actuator/health",
                                        "/crawler/**"
                                ).permitAll()
                                .requestMatchers("/user/**")
                                .hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.DEVELOPER.name(), Role.MODERATOR.name())
                                .requestMatchers(GET, "/user/**").hasAnyAuthority(USER_READ.name(), DEVELOPER_READ.name(), ADMIN_READ.name(), MODERATOR_READ.name())
                                .requestMatchers(POST, "/user/**").hasAnyAuthority(USER_CREATE.name(), DEVELOPER_CREATE.name(), ADMIN_CREATE.name(), MODERATOR_CREATE.name())
                                .requestMatchers(PUT, "/user/**").hasAnyAuthority(USER_UPDATE.name(), DEVELOPER_UPDATE.name(), ADMIN_UPDATE.name(), MODERATOR_UPDATE.name())
                                .requestMatchers(DELETE, "/user/**").hasAnyAuthority(USER_DELETE.name(), DEVELOPER_DELETE.name(), ADMIN_DELETE.name(), MODERATOR_DELETE.name())
                                .anyRequest().authenticated()

                )
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, JwtAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/user/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

}
