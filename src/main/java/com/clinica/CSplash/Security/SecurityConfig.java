package com.clinica.CSplash.Security;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf ->csrf.disable())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->authorize.
                        requestMatchers("/api/auth/cadastrar", "/api/auth/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        // 2. Rotas Bloqueadas por Cargo/Role
                        // Só quem for ADMIN pode cadastrar ou mexer com especialidades e médicos
                        .requestMatchers(HttpMethod.POST,"/api/especialidades").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medicos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/**").hasAuthority("ROLE_ADMIN")

                        //  Rotas de Usuários Comuns (ou Admin também)
                        // Pacientes e Usuários logados podem ver as especialidades e agendar consultas

                        .requestMatchers(HttpMethod.GET, "/api/especialidades/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/consultas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")


                        //Qualquer outra rota  vai exigir apenas estar logado
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    };

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
    return configuration.getAuthenticationManager();
}
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
}
