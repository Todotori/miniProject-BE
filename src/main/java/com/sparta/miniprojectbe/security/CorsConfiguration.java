package com.sparta.miniprojectbe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer 이용하여 cors를 spring life cycle에 등록한다.

//@EnableWebMvc
//@Configuration
//public class CorsConfiguration implements WebMvcConfigurer {
//  // cors 정책에서 벗어나기 위한 구성
//  @Override
//  public void addCorsMappings(final CorsRegistry registry) {
//    registry.addMapping("/**")
//        .allowedMethods("*")
//        .allowedHeaders("*")
//        .allowedOrigins("*")
//        .exposedHeaders("*")
//        .allowCredentials(true); // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것. false로 하면 자바스크립트로 요청했을때 오지 않음.
//  }
//}


// filter로 cors 설정을 한 후 빈으로 등록, 시큐리티 설정에서 필터로 등록해준다.

@Configuration
public class CorsConfiguration {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    // package가 추가 되거나 변경 되면 변경
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}