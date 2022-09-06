package com.sparta.miniprojectbe.security;

import static org.springframework.http.HttpMethod.GET;

import com.sparta.miniprojectbe.jwt.AccessDeniedHandlerException;
import com.sparta.miniprojectbe.jwt.AuthenticationEntryPointException;
import com.sparta.miniprojectbe.jwt.TokenProvider;
import com.sparta.miniprojectbe.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfiguration {

  @Value("${jwt.secret}")
  String SECRET_KEY;
  private final TokenProvider tokenProvider;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationEntryPointException authenticationEntryPointException;
  private final AccessDeniedHandlerException accessDeniedHandlerException;

//  private final CorsConfigurationSource corsConfigurationSource;

  private final CorsConfiguration corsConfiguration;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
    return (web) -> web.ignoring()
            .antMatchers("/h2-console/**");
  }

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http.cors().configure(corsConfigurationSource);
    http.addFilter(corsConfiguration.corsFilter()); // filter로 cors 설정을 등록한다.

// CSRF 설정 Disable
    http.csrf().disable()

        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPointException)
        .accessDeniedHandler(accessDeniedHandlerException);

    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http
        .authorizeRequests()

        .antMatchers("/api/emailcheck/**").permitAll()
        .antMatchers("/api/nickcheck/**").permitAll()
        .antMatchers("/api/signup/**").permitAll()
        .antMatchers("/api/login/**").permitAll()
        .antMatchers("/api/todo/all").permitAll()
        .antMatchers(GET,"/api/todo/all").permitAll()
        .anyRequest().hasRole("MEMBER");
//       .antMatchers("/**").permitAll(); 전체허용
//        .anyRequest().authenticated()
    http
        .apply(new JwtSecurityConfiguration(SECRET_KEY, tokenProvider, userDetailsService));

    return http.build();
  }



}
