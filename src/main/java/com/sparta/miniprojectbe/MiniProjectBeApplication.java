package com.sparta.miniprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(MiniProjectBeApplication.class, args);
  }

}
