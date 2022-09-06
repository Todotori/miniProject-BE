package com.sparta.miniprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@ServletComponentScan(basePackages = "com.sparta.miniprojectbe.security.logging") // filter 확인
@EnableAspectJAutoProxy // aop
@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(MiniProjectBeApplication.class, args);
  }

}
