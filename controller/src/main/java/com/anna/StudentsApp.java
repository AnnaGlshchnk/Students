package com.anna;

import com.anna.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class StudentsApp {

  public static void main(String[] args) {
    SpringApplication.run(StudentsApp.class, args);
  }

}