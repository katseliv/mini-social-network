package ru.relex.minisocialnetwork.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.relex.minisocialnetwork.filter.JwtTokenFilter;
import ru.relex.minisocialnetwork.mapper.UserMapper;
import ru.relex.minisocialnetwork.provider.JwtTokenProvider;
import ru.relex.minisocialnetwork.repository.UserRepository;
import ru.relex.minisocialnetwork.service.JwtTokenService;
import ru.relex.minisocialnetwork.service.impl.UserDetailsServiceImpl;
import ru.relex.minisocialnetwork.utils.SecurityContextFacade;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final SecurityContextFacade securityContextFacade;
    private final JwtTokenService jwtTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public AuthenticationManager authenticationManager(final HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilterBean() {
        return new JwtTokenFilter(securityContextFacade, userDetailsServiceBean(), jwtTokenService, jwtTokenProvider);
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl(userRepository, userMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/login", "/api/v1/new-access-token", "/api/v1/users/register").permitAll()
                .antMatchers("/api/v1/logout").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/users/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/users").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/users/update-password").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/users").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/messages").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/messages/history/{receiverId}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/friends/{friendId}").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/friends/hide").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/friends/list").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/friends/view/{userId}").authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))

                .and()
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .addFilterAfter(jwtTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)

                .headers()
                .cacheControl();
        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept"));
        corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "PATCH", "GET", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}
