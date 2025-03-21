package be.icc.Pid_Reservations_2024.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Spring Security configuration class to set up custom security settings for the application.
 * This configuration enables web security, defines URL authorization rules, and configures
 * authentication mechanisms, including form login, password encoding, and "remember me" functionality.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
//        AuthenticationManagerBuilder authManagBuild = http.getSharedObject(AuthenticationManagerBuilder.class);
//
//        authManagBuild.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
//
//        return authManagBuild.build();
//    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http.cors(Customizer.withDefaults())
//                // Disable protect csrf
//                .csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/admin").hasRole("ADMIN");
//                    auth.requestMatchers("/user").hasRole("MEMBER");
//                    // This is for API
//                    auth.requestMatchers("/api/public/***").permitAll(); // access for everyone
//                    auth.requestMatchers("/api/admin/***").hasRole("ADMIN"); // access only for Admin
//                    auth.anyRequest().permitAll();
//                })
//                .httpBasic(Customizer.withDefaults()) // Allow basic authentication (useful for testing)
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .usernameParameter("login")
//                        .failureUrl("/login?loginError=true"))
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logoutSuccess=true")
//                        .deleteCookies("JSESSIONID"))
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(
//                                new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
//                .build();
//    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/user/**").hasRole("MEMBER");
                    auth.requestMatchers("/reservation/**").hasAnyRole("ADMIN", "MEMBER");
                    auth.anyRequest().permitAll();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("login")
                        .failureUrl("/login?loginError=true"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
                .build();
    }

}
