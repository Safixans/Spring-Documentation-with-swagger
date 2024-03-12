package SpringDoc.uz.pdp.docs.securityconfig;


import SpringDoc.uz.pdp.docs.controller.AuthController;
import SpringDoc.uz.pdp.docs.dto.AppErrorDto;
import SpringDoc.uz.pdp.docs.enuns.Role;
import SpringDoc.uz.pdp.docs.handler.MyAccessDeniedHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final JwtTokenUtil jwtTokenUtil;
    public SecurityConfig(ObjectMapper objectMapper,
                          MyAccessDeniedHandler myAccessDeniedHandler,
                          JwtTokenUtil jwtTokenUtil) {
        this.objectMapper = objectMapper;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(matcherRegistry ->
                        matcherRegistry
                                .requestMatchers(AuthController.TOKEN).permitAll()
                                .requestMatchers("/api/stores/**").hasRole(Role.SUPER_ADMIN.name())
                                .requestMatchers("/auth/**").hasAnyRole(Role.ADMIN.name(),Role.USER.name())
//                                .requestMatchers(HttpMethod.GET, AppConstants.CASHIER_GET_PAGES).hasAnyRole("CASHIER")
//                                .requestMatchers(HttpMethod.POST, "/payment", "/ketmon", "/tesha").hasAnyRole("CASHIER")
                                .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)

                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil,userDetailsService()), UsernamePasswordAuthenticationFilter.class)

                .sessionManagement(httpSession ->
                        httpSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(myAccessDeniedHandler);
                });
//         .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){

        return (request, response, authException) -> {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage();
            int errorCode = 401;
            AppErrorDto appErrorDTO = new AppErrorDto(errorPath, errorMessage, errorCode);
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, appErrorDTO);

        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin= User.builder()
                .username("admin")
                .roles(String.valueOf(Role.ADMIN))
                .password("123")
                .build();

        UserDetails superAdmin=User
                .builder()
                .username("superadmin")
                .roles(Role.SUPER_ADMIN.toString())
                .build();
        UserDetails user=User.builder()
                .username("user")
                .roles(Role.USER.toString())
                .build();

        return new InMemoryUserDetailsManager(admin, superAdmin, user);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of(
                "http://localhost:9090",
                "http://localhost:9095",
                "http://localhost:8080"
        ));
        corsConfiguration.setAllowedHeaders(List.of("*"
                /*"Accept",
                "Content-Type",
                "Authorization"*/
        ));
        corsConfiguration.setAllowedMethods(List.of(
                "GET","POST","DELETE","PUT"
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/",corsConfiguration);
        return source;
    }

}
